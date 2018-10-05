package it.unito.di.semphiloclassifier.nlp.entities;

public class PhiloEntity extends PhiloItem {
	
	public PhiloEntity(String value, int cs, int ce) {
		super(value,PhiloTag.PHILO_ENTITY,cs,ce);
	}

}
