package com.chamelaeon.dicebot.dice.behavior;

/** 
 * L5R Mastery explosion behavior. 
 * @author Chamelaeon 
 */
public class Mastery extends AbstractExplosion {
    /** Private constructor. */
    private Mastery() { 
        super(9); 
    }

    @Override
    public String toString() { 
        return "m"; 
    }

    /**
     * Creates a factory for making Mastery behaviors.
     * @return the factory.
     */
    public static BehaviorFactory getFactory() {
        return new BehaviorFactory() {
            @Override
            public Behavior createBehavior(int threshold) {
                return new Mastery();
            }
        };
    }
}