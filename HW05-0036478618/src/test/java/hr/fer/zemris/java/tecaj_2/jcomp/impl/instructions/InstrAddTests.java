package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

public class InstrAddTests {

	@Test
	public void testInstrAdd() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getRegisterValue(1)).thenReturn(1);
		when(r.getRegisterValue(2)).thenReturn(2);
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new RegisterArgument(1));
		lista.add(new RegisterArgument(2));
		
		InstrAdd inst = new InstrAdd(lista);
		boolean b = inst.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(3)).getRegisters();
		verify(r, times(1)).getRegisterValue(1);
		verify(r, times(1)).getRegisterValue(2);
		verify(r, times(1)).setRegisterValue(0, 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrAddException1() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new NumberArgument(0));
		lista.add(new RegisterArgument(1));
		lista.add(new RegisterArgument(2));
		
		new InstrAdd(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrAddException2() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(1));
		lista.add(new NumberArgument(0));
		lista.add(new RegisterArgument(2));
		
		new InstrAdd(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrAddException3() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(1));
		lista.add(new RegisterArgument(2));
		lista.add(new NumberArgument(0));
		
		new InstrAdd(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrAddException4() {
		List<InstructionArgument> lista = new ArrayList<>();
		new InstrAdd(lista);
	}
	
}
