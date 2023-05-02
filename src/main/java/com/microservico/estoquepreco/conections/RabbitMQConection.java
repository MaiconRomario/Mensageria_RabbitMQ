package com.microservico.estoquepreco.conections;



import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import com.microservico.estoquepreco.constants.RabbitMQConstant;

import jakarta.annotation.PostConstruct;

@Component
public class RabbitMQConection {
	
	private static final String NOME_EXCHANGE = "amq.direct";
	
	private AmqpAdmin amqpAdmin;
	
	
	public RabbitMQConection(AmqpAdmin amqpAdmin) {
		this.amqpAdmin = amqpAdmin;
		
	}
	
	private Queue fila(String nomeFila) {
		return new Queue(nomeFila, true, false, false); 
	}
	
	private DirectExchange trocaDireta() {
		return new DirectExchange(NOME_EXCHANGE);
	}	
	
	private Binding relacionameto(Queue fila, DirectExchange troca ) {
		return new Binding(fila.getName(), Binding.DestinationType.QUEUE, troca.getName(), fila.getName(), null);
	}
	
	@PostConstruct
	private void adiciona() {
	  Queue filaEstoque =  this.fila(RabbitMQConstant.FILA_ESTOQUE);
	  Queue filaPreco =  this.fila(RabbitMQConstant.FILA_PRECO);
	  
	  DirectExchange troca = this.trocaDireta();
	  
	  	Binding ligaçãoEstoque =	this.relacionameto(filaEstoque, troca);
	  	Binding ligaçãoPreco =	this.relacionameto(filaPreco, troca);
	  	
	  	//Criando a fila no rabbitMQ
	  	this.amqpAdmin.declareQueue(filaEstoque);
		this.amqpAdmin.declareQueue(filaPreco);
		
		this.amqpAdmin.declareExchange(troca);
		
		this.amqpAdmin.declareBinding(ligaçãoEstoque);
		this.amqpAdmin.declareBinding(ligaçãoPreco);
	}
}
