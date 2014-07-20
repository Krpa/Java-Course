package hr.fer.zemris.java.aplikacija5.dao.sql;

import hr.fer.zemris.java.aplikacija5.dao.DAO;
import hr.fer.zemris.java.aplikacija5.dao.DAOException;
import hr.fer.zemris.java.aplikacija5.model.PollZapis;
import hr.fer.zemris.java.aplikacija5.model.Zapis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLDAO implements DAO {

	@Override
	public List<PollZapis> dohvatiAnkete() throws DAOException {
		List<PollZapis> zapisi = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls order by id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollZapis zapis = new PollZapis();
						zapis.setId(rs.getLong(1));
						zapis.setTitle(rs.getString(2));
						zapis.setMessage(rs.getString(3));
						zapisi.add(zapis);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata polla.", ex);
		}
		return zapisi;
	}
	
	@Override
	public PollZapis dohvatiPoll(long pollID) throws DAOException {
		PollZapis zapis = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=?");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						zapis = new PollZapis();
						zapis.setId(rs.getLong(1));
						zapis.setTitle(rs.getString(2));
						zapis.setMessage(rs.getString(3));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata polla.", ex);
		}
		return zapis;
	}
	
	@Override
	public List<Zapis> dohvatiZapise(long pollID) throws DAOException {
		List<Zapis> zapisi = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, votesCount from PollOptions where pollID=?");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Zapis zapis = new Zapis();
						zapis.setId(rs.getLong(1));
						zapis.setIme(rs.getString(2));
						zapis.setLink(rs.getString(3));
						zapis.setBrojGlasova((int)rs.getLong(4));
						zapis.setPollID(pollID);
						zapisi.add(zapis);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata poll-a koji ima id="+pollID, ex);
		}
		return zapisi;
	}

	@Override
	public Zapis dohvatiZapis(long id) throws DAOException {
		Zapis zapis = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, votesCount, pollID from PollOptions where id=?");
			pst.setLong(1, id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						zapis = new Zapis();
						zapis.setId(rs.getLong(1));
						zapis.setIme(rs.getString(2));
						zapis.setLink(rs.getString(3));
						zapis.setBrojGlasova((int)rs.getLong(4));
						zapis.setPollID(rs.getLong(5));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata zapisa");
		}
		return zapis;
	}

	@Override
	public void osvjeziBrojGlasova(Zapis zapis) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("update PollOptions set votesCount=? where id=?");
			pst.setLong(1, zapis.getBrojGlasova());
			pst.setLong(2, zapis.getId());
			pst.executeUpdate();
			try { pst.close(); } catch(Exception ignorable) {}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom osvježavanja zapisa!");
		}
	}
	
	@Override
	public List<Zapis> dohvatiPoBrojuGlasova(long pollID) throws DAOException {
		List<Zapis> zapisi = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, optionTitle, optionLink, votesCount, pollID from PollOptions where pollID=? ORDER BY votesCount DESC, optionTitle, id, optionLink");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						Zapis zapis = new Zapis();
						zapis.setId(rs.getLong(1));
						zapis.setIme(rs.getString(2));
						zapis.setLink(rs.getString(3));
						zapis.setBrojGlasova((int)rs.getLong(4));
						zapis.setPollID(rs.getLong(5));
						zapisi.add(zapis);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata poll-a koji ima id="+pollID, ex);
		}
		return zapisi;
	}
	
	@Override
	public void initPolls() throws DAOException {
		List<PollZapis> ankete = dohvatiAnkete();

		Connection con = SQLConnectionProvider.getConnection();
		boolean postojeBendovi = false;
		boolean postojeFilmovi = false;
		for(PollZapis zapis : ankete) {
			if("Glasanje za omiljeni bend:".equals(zapis.getTitle())) {
				postojeBendovi = true;
			}
			if("Glasanje za omiljeni film:".equals(zapis.getTitle())) {
				postojeFilmovi = true;
			}
		}
		
		if(!postojeBendovi) {
			PreparedStatement pst = null;
			Long bendID = null;
			try {
				pst = con.prepareStatement("INSERT INTO Polls (title, message) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, "Glasanje za omiljeni bend:");
				pst.setString(2, "Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!");
				pst.executeUpdate();
				ResultSet rset = pst.getGeneratedKeys();
				try {
					if(rset != null && rset.next()) {
						bendID = rset.getLong(1);
						System.out.println("Unos je obavljen i podatci su pohranjeni pod ključem id="+bendID);
					}
				} finally {
					try { rset.close(); } catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			} catch(Exception ex) {
				throw new DAOException("Pogreška prilikom ubacivanja zapisa");
			}
			if(bendID == null) {
				throw new DAOException("Nije uspijelo ubacivanje!");
			}
			try {
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "The Beatles");
				pst.setString(2, "http://www.geocities.com/~goldenoldies/TwistAndShout-Beatles.mid");
				pst.setLong(3, bendID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "The Platters");
				pst.setString(2, "http://www.geocities.com/~goldenoldies/SmokeGetsInYourEyes-Platters-ver2.mid");
				pst.setLong(3, bendID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "The Beach Boys");
				pst.setString(2, "http://www.geocities.com/~goldenoldies/SurfinUSA-BeachBoys.mid");
				pst.setLong(3, bendID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "The Four Seasons");
				pst.setString(2, "http://www.geocities.com/~goldenoldies/BigGirlsDontCry-FourSeasons.mid");
				pst.setLong(3, bendID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "The Marcels");
				pst.setString(2, "http://www.geocities.com/~goldenoldies/Bluemoon-Marcels.mid");
				pst.setLong(3, bendID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "The Everly Brothers");
				pst.setString(2, "http://www.geocities.com/~goldenoldies/All.I.HaveToDoIsDream-EverlyBrothers.mid");
				pst.setLong(3, bendID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "The Mamas And The Papas");
				pst.setString(2, "http://www.geocities.com/~goldenoldies/CaliforniaDreaming-Mamas-Papas.mid");
				pst.setLong(3, bendID);
				pst.setLong(4, 0);
				pst.executeUpdate();
			} catch(Exception ex) {
				throw new DAOException("Pogreška prilikom ubacivanja zapisa");
			}  finally {
				try { pst.close(); } catch(Exception ignorable) {}
			} 
		}
		
		if(!postojeFilmovi) {
			PreparedStatement pst = null;
			Long filmID = null;
			try {
				pst = con.prepareStatement("INSERT INTO Polls (title, message) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
				pst.setString(1, "Glasanje za omiljeni film:");
				pst.setString(2, "Od sljedećih Harry Potter filmova, koji Vam je nastavak najdraži? Kliknite na link kako biste glasali!");
				pst.executeUpdate();
				ResultSet rset = pst.getGeneratedKeys();
				try {
					if(rset != null && rset.next()) {
						filmID = rset.getLong(1);
						System.out.println("Unos je obavljen i podatci su pohranjeni pod ključem id="+filmID);
					}
				} finally {
					try { rset.close(); } catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			} catch(Exception ex) {
				throw new DAOException("Pogreška prilikom ubacivanja zapisa");
			}
			if(filmID == null) {
				throw new DAOException("Nije uspijelo ubacivanje!");
			}
			try {
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "Harry Potter i Kamen mudraca");
				pst.setString(2, "http://www.youtube.com/watch?v=GmYVg50UleM");
				pst.setLong(3, filmID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "Harry Potter i Odaja tajni");
				pst.setString(2, "http://www.youtube.com/watch?v=dmPrfYkpwTY");
				pst.setLong(3, filmID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "Harry Potter i Zatočenik azkabana");
				pst.setString(2, "http://www.youtube.com/watch?v=p4mpOY0KWsM");
				pst.setLong(3, filmID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "Harry Potter i Plameni pehar");
				pst.setString(2, "http://www.youtube.com/watch?v=aFhCLiGvb08");
				pst.setLong(3, filmID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "Harry Potter i Red feniksa");
				pst.setString(2, "http://www.youtube.com/watch?v=CQAX-YwX6iM");
				pst.setLong(3, filmID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "Harry Potter i Princ Miješane krvi");
				pst.setString(2, "http://www.youtube.com/watch?v=jpCPvHJ6p90");
				pst.setLong(3, filmID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "Harry Potter i Darovi smrti 1");
				pst.setString(2, "http://www.youtube.com/watch?v=YzfEH0UPEBo");
				pst.setLong(3, filmID);
				pst.setLong(4, 0);
				pst.executeUpdate();
				pst = con.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?,?,?,?)");
				pst.setString(1, "Harry Potter i Darovi smrti 2");
				pst.setString(2, "http://www.youtube.com/watch?v=5NYt1qirBWg");
				pst.setLong(3, filmID);
				pst.setLong(4, 0);
				pst.executeUpdate();
			} catch(Exception ex) {
				throw new DAOException("Pogreška prilikom ubacivanja zapisa");
			}  finally {
				try { pst.close(); } catch(Exception ignorable) {}
			} 
		}
	}
	
}
