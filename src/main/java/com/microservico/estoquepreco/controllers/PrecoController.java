package com.microservico.estoquepreco.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservico.estoquepreco.constants.RabbitMQConstant;
import com.microservico.estoquepreco.dto.EstoqueDto;
import com.microservico.estoquepreco.dto.PrecoDto;
import com.microservico.estoquepreco.services.RabbitMQService;

@RestController
@RequestMapping(value= "preco")
public class PrecoController {
	
	@Autowired
	private RabbitMQService rabbitMQService;

	@PutMapping()
	private ResponseEntity alterarPreco(@RequestBody PrecoDto precoDto) {
		System.out.println(precoDto.codigoDoProduto);
		this.rabbitMQService.enviarMensagem(RabbitMQConstant.FILA_PRECO, precoDto);
		return new ResponseEntity(HttpStatus.OK);
			
	}
}
