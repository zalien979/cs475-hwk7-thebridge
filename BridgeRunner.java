import java.util.Random;

/**
 * Runs all threads
 */

public class BridgeRunner {

	public static void main(String[] args) {
		if(args.length<2){
			System.out.println("please enter two integers, bye");
			System.exit(1);
		}
		Random rand = new Random();
		int limit=Integer.parseInt(args[0]);
		int traffic=Integer.parseInt(args[1]);
		OneLaneBridge bridge=new OneLaneBridge(limit);
		
		Thread[] cars=new Thread[traffic];
		for(int i=0; i<traffic; i++){
			cars[i]= new Thread(new Car(rand.nextInt(), bridge));
			cars[i].start();
		}
		
		for(int i=0; i<traffic; i++){
			try {
				cars[i].join();
			}
			catch(InterruptedException e){

			}
		}

		System.out.println("All cars have crossed!!");
	}

}