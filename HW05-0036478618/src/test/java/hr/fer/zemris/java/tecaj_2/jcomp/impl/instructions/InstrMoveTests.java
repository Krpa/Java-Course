package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;


public class InstrMoveTests {

	@Test
	public void testInstrLoad() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getRegisterValue(1)).thenReturn(23);
		
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(0));
		lista.add(new RegisterArgument(1));
		
		InstrMove instr = new InstrMove(lista);
		boolean b = instr.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(2)).getRegisters();
		verify(r, times(1)).getRegisterValue(1);
		verify(r, times(1)).setRegisterValue(0, 23);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrMoveException1() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new RegisterArgument(1));
		lista.add(new NumberArgument(2));
		
		new InstrMove(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrMoveException2() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new NumberArgument(0));
		lista.add(new RegisterArgument(1));
		
		new InstrMove(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrAddException3() {
		List<InstructionArgument> lista = new ArrayList<>();
		new InstrMove(lista);
	}
	
}
