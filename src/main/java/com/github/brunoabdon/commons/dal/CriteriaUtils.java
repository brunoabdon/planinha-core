package com.github.brunoabdon.commons.dal;

import java.util.Optional;
import java.util.function.BiFunction;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;


@ApplicationScoped
public class CriteriaUtils {

	private static final Optional<Predicate> NONE = Optional.empty();
	
	public <X> Optional<Predicate> eq(
			final CriteriaBuilder cb, 
			final Expression<X> exp,
			final X valor){
    	
		return op(cb::equal,exp, valor);
	}

	public <X extends Comparable<? super X>> Optional<Predicate> greaterThan(
			final CriteriaBuilder cb, 
			final Expression<X> exp,
			final X valor){
    	
		return op(cb::greaterThan,exp, valor);
	}
	
	public <X extends Comparable<? super X>> Optional<Predicate> 
				greaterThanOrEqualTo(
					final CriteriaBuilder cb, 
					final Expression<X> exp,
					final X valor){
    	
		return op(cb::greaterThanOrEqualTo,exp, valor);
	}
	
	public <X extends Comparable<? super X>> Optional<Predicate> lessThan(
			final CriteriaBuilder cb, 
			final Expression<X> exp,
			final X valor){
    	
		return op(cb::lessThan,exp, valor);
	}
	
	public <X extends Comparable<? super X>> Optional<Predicate> 
				lessThanOrEqualTo(
					final CriteriaBuilder cb, 
					final Expression<X> exp,
					final X valor){
    	
		return op(cb::lessThanOrEqualTo,exp, valor);
	}
	
	public <X extends Comparable<? super X>> Optional<Predicate> between(
			final CriteriaBuilder cb, 
			final Expression<X> exp,
			final X valor1,
			final X valor2){
    	
		return 
			valor1 != null && valor2 != null
				? Optional.of(cb.between(exp, valor1, valor2)) 
				: NONE;
	}
	
	private <X> Optional<Predicate> op(
			final BiFunction<Expression<X>, X, Predicate> op,
			final Expression<X> exp,
			final X valor){
    	
		return valor != null ? Optional.of(op.apply(exp, valor)) : NONE;
	}
}
