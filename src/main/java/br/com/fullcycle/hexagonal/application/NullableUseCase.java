package br.com.fullcycle.hexagonal.application;

public abstract class NullableUseCase<OUTPUT> {

    public abstract OUTPUT execute();
}
