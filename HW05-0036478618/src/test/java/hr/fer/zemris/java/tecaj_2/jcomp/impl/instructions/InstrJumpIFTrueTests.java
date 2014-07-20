package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InstrJumpIFTrueTests {

	@Test
	public void testInstrJumpIfT() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		when(r.getFlag()).thenReturn(true);
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new NumberArgument(0));
		
		InstrJumpIfTrue instr = new InstrJumpIfTrue(lista);
		boolean b = instr.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(2)).getRegisters();
		verify(r, times(1)).getFlag();
		verify(r, times(1)).setProgramCounter(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrJPITException1() {
		List<InstructionArgument> lista = new ArrayList<>();
		new InstrJumpIfTrue(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrJPITException2() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new StringArgument("bla"));
		new InstrJumpIfTrue(lista);
	}
}
