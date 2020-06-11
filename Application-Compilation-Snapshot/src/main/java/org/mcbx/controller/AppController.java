/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mcbx.controller;

import org.mcbx.service.AuthorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppController {

    @Autowired
    private AuthorsService authorsService;

    public void callAuthorsServiceMethod() {
        authorsService.testCallMethod();
    }

}
