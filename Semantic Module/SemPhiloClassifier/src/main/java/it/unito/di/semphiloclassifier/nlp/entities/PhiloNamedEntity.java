package it.unito.di.semphiloclassifier.nlp.entities;

public class PhiloNamedEntity extends PhiloEntity {
	
	public PhiloNamedEntity(String value, int cs, int ce) {
		super(value,PhiloTag.PHILO_NAMED_ENTITY,cs,ce);
	}

}
