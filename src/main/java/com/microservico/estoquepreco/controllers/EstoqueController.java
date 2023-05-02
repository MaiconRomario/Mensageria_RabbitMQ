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
import com.microservico.estoquepreco.services.RabbitMQService;

@RestController
@RequestMapping(value= "estoque")
public class EstoqueController {
	
	@Autowired 
	private RabbitMQService rabbitService;

	@PutMapping()
	private ResponseEntity alterarEstoque(@RequestBody EstoqueDto estoqueDto) {
		System.out.println(estoqueDto.codigoDoProduto);
		
		this.rabbitService.enviarMensagem(RabbitMQConstant.FILA_ESTOQUE, estoqueDto);
		return new ResponseEntity(HttpStatus.OK);
		
		
	}
}
