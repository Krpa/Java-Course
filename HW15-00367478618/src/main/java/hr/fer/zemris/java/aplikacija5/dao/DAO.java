package hr.fer.zemris.java.aplikacija5.dao;

import hr.fer.zemris.java.aplikacija5.model.PollZapis;
import hr.fer.zemris.java.aplikacija5.model.Zapis;

import java.util.List;

public interface DAO {
	
	public List<PollZapis> dohvatiAnkete() throws DAOException;
	
	public PollZapis dohvatiPoll(long pollID) throws DAOException;

	public List<Zapis> dohvatiZapise(long pollID) throws DAOException;
	
	public Zapis dohvatiZapis(long id) throws DAOException;
	
	public void osvjeziBrojGlasova(Zapis zapis) throws DAOException;
	
	public void initPolls() throws DAOException;

	public List<Zapis> dohvatiPoBrojuGlasova(long pollID) throws DAOException;
}
