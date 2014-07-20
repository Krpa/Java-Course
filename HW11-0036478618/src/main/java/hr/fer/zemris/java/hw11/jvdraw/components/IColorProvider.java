package hr.fer.zemris.java.hw11.jvdraw.components;

import hr.fer.zemris.java.hw11.jvdraw.listeners.ColorType;

import java.awt.Color;

public interface IColorProvider {
	public Color getCurrentColor();
	public ColorType getType();
}