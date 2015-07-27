package org.kimlavoie.permutations;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.*;
import java.util.Arrays;
import java.util.List;

public class PermutationGeneratorTest {
    private Integer[] original;
    private PermutationGenerator<Integer> permutationGenerator;
    
    @Before
    public void init(){
        Integer[] temp = {1,2,3,4};
        this.original = temp;
        this.permutationGenerator = new PermutationGenerator<Integer>(original);
    }

    @Test
    public void nextIsNotOriginalTest(){
        Integer[] permutation = permutationGenerator.next();

        assertThat(original, not(equalTo(permutation)));
    }

    @Test
    public void nextIsPermutationTest(){
        Integer[] permutation = permutationGenerator.next();
        Arrays.sort(permutation);

        assertThat(original, equalTo(permutation));
    }

    @Test
    public void firstAndSecondNextNotEqualsTest(){
        Integer[] first = permutationGenerator.next();
        Integer[] second = permutationGenerator.next();

        assertThat(first, not(equalTo(second)));
    }
    
    @Test
    public void firstAndSecondNextArePermutationsTest(){
        Integer[] first = permutationGenerator.next();
        Integer[] second = permutationGenerator.next();
        Arrays.sort(first);
        Arrays.sort(second);

        assertThat(first, equalTo(second));
    }

    @Test(timeout=1)
    public void enoughPermutationsTest(){
        for(int i = 0; i < 23; i++){
            permutationGenerator.next();
        }
    }

    @Test(expected=LastPermutationException.class)
    public void throwExceptionAtTheEndTest(){
        for(int i = 0; i < 24; i++){
            permutationGenerator.next();
        }
    }

    @Test
    public void allPermutationsSizeTest(){
        List<Integer[]> allPermutations = permutationGenerator.getAll();
        assertEquals(allPermutations.size(), 24);
    }

    @Test
    public void allPermutationsAreAllPermutationsTest(){
        List<Integer[]> allPermutations = permutationGenerator.getAll();
        areAllPermutationsTest(allPermutations);
    }

    @Test
    public void allNextSizeTest(){
        permutationGenerator.next();
        List<Integer[]> allNext = permutationGenerator.getAllNext();

        assertEquals(allNext.size(), 23);
    }

    @Test
    public void allNextAreAllPermutationsTest(){
        List<Integer[]> allNext = permutationGenerator.getAllNext();
        areAllPermutationsTest(allNext);
    }

    public void areAllPermutationsTest(List<Integer[]> permutations){
        for(int i = 0; i < permutations.size(); i++){
            for(int j = i+1; j < permutations.size(); j++){
                Integer[] first = permutations.get(i).clone();
                Integer[] second = permutations.get(j).clone();

                assertThat(first, not(equalTo(second)));

                Arrays.sort(first);
                Arrays.sort(second);

                assertThat(first, equalTo(second));

            }
        }
    }
}

