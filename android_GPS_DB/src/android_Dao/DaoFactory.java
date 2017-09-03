package android_Dao;

public class DaoFactory {
	public Dao dao(){
		return new Dao(connectionMaker());
	}
	public ConnectionMaker connectionMaker(){
		return new DConnectionMaker();
	}
}
