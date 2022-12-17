package day17;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import helper.Puzzle;

public class PyroclasticFlow extends Puzzle {
    String jetPattern;

    protected PyroclasticFlow(String input) throws IOException {
        super(input);
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(getInputFile()), StandardCharsets.UTF_8));) {
            String line;

            while ((line = br.readLine()) != null) {
                jetPattern = line;
            }
        }
    }

    private List<RockShape> initializeRockPattern() {
        List<RockShape> rockPatterns = new ArrayList<>();
        rockPatterns.add(RockShape.MINUS);
        rockPatterns.add(RockShape.PLUS);
        rockPatterns.add(RockShape.L);
        rockPatterns.add(RockShape.LINE);
        rockPatterns.add(RockShape.SQUARE);
        return rockPatterns;
    }

    private long runSimulation(double maxNumberOfStone, boolean detectLoop) {
        Board b = new Board();
        List<RockShape> rockPatterns = initializeRockPattern();
        LoopDetection loopDetection = new LoopDetection();

        double time = 0;
        for (double numberOfStone = 0; numberOfStone < maxNumberOfStone; numberOfStone++) {

            if (detectLoop) {
                Snapshot snap = new Snapshot((int)(numberOfStone % rockPatterns.size()),
                        (int)time % jetPattern.length(), b.height, time, numberOfStone, b.getTopNivel());

                Object find = loopDetection.detectLoop(snap);
                if (find == null) {
                    loopDetection.addItem(snap);
                } else {
                    double currentHeight = snap.height;
                    // System.out.println(b.visualized());

                    double diffstone = snap.stoneNumber - ((Snapshot)find).stoneNumber;
                    double diffHeight = snap.height - ((Snapshot)find).height;

                    long nbloop = (long)(maxNumberOfStone - numberOfStone) / (long)diffstone;


                    double currenTStone = numberOfStone + diffstone * nbloop;
                    double rest = maxNumberOfStone - currenTStone;
                    double restHeight = 0;
                    double indexSearched = ((Snapshot)find).stoneNumber + rest;
                    for (Object tempSnap : loopDetection.map.keySet()) {
                        if (((Snapshot)tempSnap).stoneNumber == indexSearched) {
                            restHeight = ((Snapshot)tempSnap).height - ((Snapshot)find).height;
                            break;
                        }
                    }
                    return (long)(currentHeight + nbloop * diffHeight + restHeight);
                }
            }


            Rock rock = Rock.createRock(rockPatterns.get((int)(numberOfStone % rockPatterns.size())), (int)b.height);
            boolean falling = true;
            while (falling) {

                char direction = jetPattern.charAt((int)(time % jetPattern.length()));
                time++;


                if (direction == '<') {
                    if (rock.canMoveLeft(b)) {
                        rock.moveLeft();
                    }
                } else {
                    if (rock.canMoveRight(b)) {
                        rock.moveRight();
                    }
                }
                if (rock.canFall(b)) {
                    rock.fall();
                } else {
                    falling = false;
                }
            }

            b.addRock(rock);

        }
        return (long)b.height;
    }

    @Override
    public Object getAnswer1() {
        return runSimulation(2022, true);
    }

    @Override
    public Object getAnswer2() {
        return runSimulation(1000000000000L, true);
    }


    public static void main(final String[] args) throws IOException {
        PyroclasticFlow pyroclasticFlow = new PyroclasticFlow("day17/input");
        System.out.println("Answer1: " + pyroclasticFlow.getAnswer1());
        System.out.println("Answer2: " + pyroclasticFlow.getAnswer2());
    }

}
