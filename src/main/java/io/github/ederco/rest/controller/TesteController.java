package io.github.ederco.rest.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/testes")
public class TesteController {
    @RequestMapping(value = "/testeRest/{tes}",method = RequestMethod.GET)
    @ResponseBody
    public String testeRest( @PathVariable("tes") String testeNome){
        return String.format("Nome: %s",testeNome);
    }
}
