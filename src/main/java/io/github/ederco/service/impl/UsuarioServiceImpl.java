package io.github.ederco.service.impl;

import io.github.ederco.domain.entity.Usuario;
import io.github.ederco.domain.repository.UsuarioRepository;
import io.github.ederco.exception.SenhaInvalidaException.SenhaInvalidaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvar(Usuario usuario){
        return repository.save(usuario);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername((usuario.getLogin()));
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());
        if(senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if(!username.equals("cicrano")){
//            throw new UsernameNotFoundException("Usuário não encontrado na base");
//        }
//        return User
//                .builder()
//                .username("cicrano")
//                .password(encoder.encode("123"))
//                .roles("USER", "ADMIN")
//                .build();
        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado na base de dados."));

        String[] roles = usuario.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

}
