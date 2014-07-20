package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Memory;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InstrLoadTests {
	
	@Test
	public void testInstrLoad() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		Memory m = mock(Memory.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(c.getMemory()).thenReturn(m);
		
		when(m.getLocation(42)).thenReturn(64);
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new NumberArgument(42));
		
		InstrLoad instr = new InstrLoad(lista);
		boolean b = instr.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(1)).getRegisters();
		verify(c, times(1)).getMemory();
		verify(m, times(1)).getLocation(42);
		verify(r, times(1)).setRegisterValue(0, 64);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrLoadException1() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(1));
		lista.add(new RegisterArgument(2));
		
		new InstrAdd(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrLoadException2() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new NumberArgument(0));
		lista.add(new RegisterArgument(1));
		
		new InstrLoad(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrAddException3() {
		List<InstructionArgument> lista = new ArrayList<>();
		new InstrLoad(lista);
	}
	
	
}
