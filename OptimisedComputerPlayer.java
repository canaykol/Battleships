import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Random;
import java.io.Serializable;
import java.util.Collections;
import java.util.TreeMap;

/**
 * Write a description of class OptimisedComputerPlayer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OptimisedComputerPlayer extends ComputerPlayer  implements Serializable
{
    private int difficulty;

    public OptimisedComputerPlayer(Integer i)
    {
        super(createName(i));
        this.difficulty = i;
    }

    public boolean makeMove()
    {
        Board bor = getOpponent().getBoard();

        if(difficulty == 3 || difficulty == 2){
            HashSet<Ship> remainingShips = bor.getRemainingShips();
            HashSet<Ship> sunkShips = bor.getSunkShips();
            HashSet<Position> shotPlaces = bor.getShotPlaces();
            HashSet<Position> failedAttempts = bor.getFailedAttempts();
            HashSet<Position> possible = new HashSet<Position>();
            TreeMap<Position, Integer> likelihood = new TreeMap<Position, Integer>();
            HashSet<Position> suspiciousPlaces = shotPlaces;
            for(int a=1; a<11; a++){
                for(int b=1; b<11; b++){
                    Position tempPoint = new Position(a,b);
                    possible.add(tempPoint);
                    likelihood.put(tempPoint, new Integer(0));
                }
            }

            for(Position p: failedAttempts){
                possible.remove(p);
            }
            for(Position p: shotPlaces){
                possible.remove(p);
                for(Position pd: p.getDiagonals()){
                    possible.remove(pd);
                }
            }
            for(Ship s: sunkShips){
                for(Position p: s.getDeck().keySet()){
                    possible.remove(p);
                    suspiciousPlaces.remove(p);
                    for(Position ps: p.getSurroundings()){
                        possible.remove(ps);
                    }
                }
            }

            //buldu mu takip edecek;

            if(!suspiciousPlaces.isEmpty()){
                HashSet<Position> suspects = new HashSet<Position>();
                for(Position p : suspiciousPlaces){
                    suspects.addAll(p.getNeighbours());
                }
                for(Position target : suspects){
                    if(bor.isInField(target) && possible.contains(target)){
                        try{
                            boolean result = bor.fire(target);
                            System.out.println(this.getPlayerName()+" has selected the column number "+target.getX()+", and the row number "+target.getY()+".");
                            //System.out.println("By rule 1.");
                            return result;
                        }
                        catch(IllegalPositionException e){
                            System.err.println("IllegalPositionException: "+e.getMessage());
                            makeMove();
                        }
                    }
                }
            }
            if(difficulty == 3){
                boolean isThereOnlyTwoShipsLeft = remainingShips.size()<= 2;
                int largestSize = 0;

                for(Ship s: remainingShips){
                    if(s.getSize()>largestSize){
                        largestSize = s.getSize();
                    }
                }

                for(int i = 0; i<2; i++){
                    for(int a =1 ; a<11; a++){
                        for( int b =1 ; b<11; b++){
                            Position p;
                            Position direction;
                            if(i == 0){
                                p = new Position(a,b);
                                direction = new Position(0,1);
                            }
                            else{
                                p = new Position(b,a);
                                direction = new Position(1,0);
                            }

                            HashSet<Position> proposedDeck = new HashSet<Position>();
                            Position currentPosition = p;
                            for(int c=0; c < largestSize; c++){
                                proposedDeck.add(currentPosition);
                                currentPosition = currentPosition.addVector(direction);
                            }

                            boolean isPlaceable = true;
                            for(Position pd: proposedDeck){
                                if((!possible.contains(pd)) || (!bor.isInField(p))){
                                    isPlaceable = false;
                                    break;
                                }
                            }

                            if(isPlaceable){
                                for(Position pd: proposedDeck){
                                    likelihood.put(pd, new Integer(likelihood.get(pd)+1));
                                }
                            }

                            else{
                                break;
                            }
                        }
                    }
                }

                Integer highestPossibility = Collections.max(likelihood.values());
                if(highestPossibility>0){
                    for(Position p: likelihood.keySet()) {
                        if (likelihood.get(p).equals(highestPossibility)) {
                            try{
                                boolean result = bor.fire(p);
                                System.out.println(this.getPlayerName()+" has selected the column number "+p.getX()+", and the row number "+p.getY()+".");
                                return result;
                            }
                            catch(IllegalPositionException e){
                                System.err.println("IllegalPositionException: "+e.getMessage());
                                makeMove();
                            }
                        }
                    }
                }
            }

            int luck = new Random().nextInt(possible.size());
            int i = 0;
            for(Position p: possible){
                if(i == luck){
                    try{
                        System.out.println("by rule rand");
                        boolean result = bor.fire(p);
                        System.out.println(this.getPlayerName()+" has selected the column number "+p.getX()+", and the row number "+p.getY()+".");
                        return result;
                    }
                    catch(IllegalPositionException e){
                        System.err.println("IllegalPositionException: "+e.getMessage());
                        makeMove();
                    }
                }
                i++;
            }
        }
        else if(difficulty == 1){
            int luck = new Random().nextInt(bor.getUnfiredPositions().size());
            int i = 0;
            for(Position p: bor.getUnfiredPositions()){
                if(i == luck){
                    try{
                        boolean result = bor.fire(p);
                        System.out.println(this.getPlayerName()+" has selected the column number "+p.getX()+", and the row number "+p.getY()+".");
                        return result;
                    }
                    catch(IllegalPositionException e){
                        System.err.println("IllegalPositionException: "+e.getMessage());
                        makeMove();
                    }
                }
                i++;
            }
        }
        return false;
    }

    public void choosePlaces() throws IllegalPositionException{
        if(difficulty !=1){
            HashMap<Character, Boolean> isOnTheSide = new HashMap<Character, Boolean>();
            HashSet<Ship> onTheSide = new HashSet<Ship>();
            isOnTheSide.put(new Character('A'), new Boolean(new Random().nextInt(2) != 1));
            isOnTheSide.put(new Character('B'), new Boolean(new Random().nextInt(3) == 1));
            isOnTheSide.put(new Character('S'), new Boolean(new Random().nextInt(3) == 1));
            isOnTheSide.put(new Character('D'), new Boolean(new Random().nextInt(4) == 1));
            isOnTheSide.put(new Character('P'), new Boolean(new Random().nextInt(5) == 1));

            for(Character c: thisBoard.getShipCallsigns().keySet()){
                if(isOnTheSide.get(c)){
                    onTheSide.add(thisBoard.getShipCallsigns().get(c));
                }
            }

            for(Ship s: onTheSide){
                boolean isNotPlacedYet = true;            
                while(isNotPlacedYet){
                    Position p = positionSelection();
                    Position d;
                    if(p.isOnSide()){
                        if(p.isOnHorizontalSide()){
                            d = directionSelection(true);
                        }
                        else{
                            d = directionSelection(false);
                        }
                        if(thisBoard.isPlaceable(s, p, d)){
                            thisBoard.placeShip(s,p,d);
                            isNotPlacedYet = false;
                        }
                    }
                }
            }

            for(Ship s: thisBoard.getUnplacedShips()){
                boolean isNotPlacedYet = true;
                while(isNotPlacedYet){
                    Position p = positionSelection();
                    Position d = directionSelection();
                    if(thisBoard.isPlaceable(s, p, d)){
                        thisBoard.placeShip(s,p,d);
                        isNotPlacedYet = false;
                    }
                }
            }

            for(Ship s: thisBoard.getUnplacedShips()){
                boolean isNotPlacedYet = true;
                while(isNotPlacedYet){
                    Position p = positionSelection();
                    Position d = directionSelection();
                    if(thisBoard.isPlaceable(s, p, d)){
                        thisBoard.placeShip(s,p,d);
                        isNotPlacedYet = false;
                    }
                }
            }
        }
        else if(difficulty == 1){
            for(Ship s: thisBoard.getUnplacedShips()){
                boolean isNotPlacedYet = true;
                while(isNotPlacedYet){
                    Position p = positionSelection();
                    Position d = directionSelection();
                    if(thisBoard.isPlaceable(s, p, d)){
                        thisBoard.placeShip(s,p,d);
                        isNotPlacedYet = false;
                    }
                }
            }
        }

        else{}
    }

    public Position directionSelection(boolean onlyHorizontal){
        int random = new Random().nextInt(2);
        if(onlyHorizontal){
            if(random == 0){
                return new Position(-1, 0);
            }
            else if(random == 1){
                return new Position(1, 0);
            }
        }
        else{
            if(random == 0){
                return new Position(0, -1);
            }
            else if(random == 1){
                return new Position(0, 1);
            }
        }
        return null;
    }

    public static String createName(int i){
        String name = "";
        switch(i){
            case 1:  name = "Rookie";
            break;
            case 2:  name = "Advanced";
            break;
            case 3:  name = "Expert";
            break;
        }
        return name;
    }
}
