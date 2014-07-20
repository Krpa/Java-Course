package hr.fer.zemris.java.tecaj_2.jcomp.impl.instructions;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import hr.fer.zemris.java.tecaj_2.jcomp.Computer;
import hr.fer.zemris.java.tecaj_2.jcomp.InstructionArgument;
import hr.fer.zemris.java.tecaj_2.jcomp.Registers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class InstrJumpTests {

	@Test
	public void testInstrJump() {
		Computer c = mock(Computer.class);
		Registers r = mock(Registers.class);
		
		when(c.getRegisters()).thenReturn(r);
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new NumberArgument(0));
		
		InstrJump instr = new InstrJump(lista);
		boolean b = instr.execute(c);
		
		assertEquals("execute mora vratiti false da se ne prekine rad procesora", false, b);
		verify(c, times(1)).getRegisters();
		verify(r, times(1)).setProgramCounter(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrJPException1() {
		List<InstructionArgument> lista = new ArrayList<>();
		new InstrJump(lista);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInstrJPException2() {
		List<InstructionArgument> lista = new ArrayList<>();
		lista.add(new StringArgument("bla"));
		new InstrJump(lista);
	}
	
}
