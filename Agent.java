import java.rmi.RemoteException;
import java.rmi.Remote;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import dataObjects.Philosopher;
import dataObjects.Seat;

import java.rmi.Naming;


public class Agent extends UnicastRemoteObject implements AgentInterface{
	
	private ArrayList<AgentInterface> otherAgents = new ArrayList <AgentInterface>();
	private ArrayList<Philosopher> philoList = new ArrayList<Philosopher>();
	private ArrayList<Seat> seatList = new ArrayList<Seat>();
	private ArrayList<TableFork> forks = new ArrayList<TableFork>();
	private int agentID=0;
	private AgentInterface nextAgent;
	
	
	
	public Agent() throws RemoteException{
		super();
	}
	
	
	
	public Agent(int agentID) throws RemoteException{
		super();
		this.agentID=agentID;	
	}

	
	
	/**
	 * should be called before startPhilosophers() is called in order to initiallize all the philosophers that should run. 
	 */
	public void initialzePhilos(int numberOfPhilos, int firstPhiloID){
		philoList.clear();
		addPhilos(numberOfPhilos, firstPhiloID);

		System.out.println("Agent"+agentID+ " initializing "+numberOfPhilos+ " Philosophers with starting number " + firstPhiloID); //TODO
		//TODO fill change initialize to different initializations
		//TODO change init methods in master
		//TODO remove some arguments here
		//testings
	}
	
	
	
	
	/**
	 * CARE all forks have to be initialized before and next agents have to be given called.
	 * @param numberOfSeats
	 * @param firstSeatID
	 * @throws RemoteException 
	 */
	public void initSeats(int numberOfSeats, int firstSeatID) throws RemoteException {
		seatList.clear();
		for (int i = 0; i < numberOfSeats; i++) {
			if (i+1<numberOfSeats){
				seatList.add(new Seat(firstSeatID+i, forks.get(i), forks.get(i+1)));
			}else{
				seatList.add(new Seat(firstSeatID+i, forks.get(i), nextAgent.getForks().get(0)));		
		}
		System.out.println("Agent"+agentID+ " initializing "+numberOfSeats+ " Seats."); 
		System.out.println("Verifying that last Seats["+(firstSeatID+numberOfSeats-1)+ "] fork equals next agents firstfork: "+ (seatList.get(numberOfSeats-1).getRightFork().equals(nextAgent.getForks().get(0)))); 
		//TODO finish create seats and give them forks that are already initialized else we gett nullpointer. evtually create an forks not initialized exception.
	}
		
		
		
	public void initForks(int numberOfSeats) {
		forks.clear();
		for (int i = 0; i < numberOfSeats; i++) {
			TableFork temp = new TableFork;
			forks.add(temp);
			System.out.println(temp);
		}
		System.out.println("Agent"+agentID+ " initializing "+numberOfSeats+ " forks."); 
		
		//TODO finish create seats and give them forks that are already initialized else we gett nullpointer. evtually create an forks not initialized exception.
	}
	
	

	public void addPhilos(int philos, int firstPhiloID){
		for (int i = 0; i < philos; i++) {
			
			philoList.add(new Philosopher(firstPhiloID+i));
		}
	}
	
	
	
	public ArrayList<TableFork> getForks() {
		return forks;
	}
	
	
	
	public ArrayList<Seat> getSeats() {
		return seatList;
	}

	
	
	public void giveOtherAgent(AgentInterface agent){
		this.otherAgents.add(agent);
	}
	
	
	
	public void giveNextAgent(AgentInterface nextAgent) throws RemoteException{
		this.nextAgent=nextAgent;
		System.out.println("I'm agent"+ agentID + " next up is Agent" + nextAgent.getAgentID() );
	}
	
	
	
	public void startPhilosophers(){
		for (Philosopher p: philoList){
			new Thread(p).start();	
		}
	}
	
	
	
	private void fillSeatList(int philos, int firstPhiloId){
		for (int i = 0; i < philos; i++) {
			//TODO new Seat
		}
	}

	

	public int getAgentID() {
		return agentID;
	}

	
	
	
	@Override
	public String toString() {
		return "Agent"+agentID ;
	}
	
	
	
	
	public ArrayList<AgentInterface> getOtherAgents() {
		return otherAgents;
	}
}