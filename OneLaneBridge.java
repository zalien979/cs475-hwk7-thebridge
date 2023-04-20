import java.util.ArrayList;
/**
 * bridge with only one lane
 * @author Zale Fadiman
 */

public class OneLaneBridge extends Bridge {
    
    private Object road= new Object();
    private int limit;
    public OneLaneBridge(int limit){
      super();
      this.limit=limit;

    }
   
  public void arrive(Car car) throws InterruptedException{
    int skip =0;
    synchronized(this.road){
        if(this.bridge.size()==0){
            this.direction=car.getDirection();
            this.bridge.add(car);
            car.setEntryTime(this.currentTime);
            this.currentTime++;
            check();
            skip=1;
        }
    }
    if (skip!=1){
        synchronized(this.road){
        while(car.getDirection()!=this.direction){
            try{
                this.road.wait();
            }
            catch(InterruptedException e){
                
            }
        }
        }
        synchronized(this.road){
            while(this.bridge.size()>=this.limit){
                try{
                    this.road.wait();
                }
                catch(InterruptedException e){
                    
                }
            }
            this.bridge.add(car);
            car.setEntryTime(this.currentTime);
            this.currentTime++;
            check();
        }
    }
  }

  public void exit(Car car) throws InterruptedException{
    synchronized(this.road){
        this.bridge.remove(car);
        if(this.bridge.size()==0){
            if(this.direction){
                this.direction=false;
            }
            else{
                this.direction=true;
            }
        }
        this.road.notifyAll();
    }
  }
  private void check(){
    System.out.print("Bridge dir="+this.direction+"[");
    for(int i=0; i<this.bridge.size();i++){
        System.out.print(this.bridge.get(i).toString()+",");
    }
    System.out.println("]");
  }
}
