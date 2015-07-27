package org.kimlavoie.permutations;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.CoreMatchers.*;
import java.util.Arrays;
import java.util.List;

public class PermuterTest {
    private Integer[] original;
    private Permuter<Integer> permuter;
    
    @Before
    public void init(){
        Integer[] temp = {1,2,3,4};
        this.original = temp;
        this.permuter = new Permuter<Integer>(original);
    }

    @Test
    public void nextIsNotOriginalTest(){
        Integer[] permutation = permuter.next();

        assertThat(original, not(equalTo(permutation)));
    }

    @Test
    public void nextIsPermutationTest(){
        Integer[] permutation = permuter.next();
        Arrays.sort(permutation);

        assertThat(original, equalTo(permutation));
    }

    @Test
    public void firstAndSecondNextNotEqualsTest(){
        Integer[] first = permuter.next();
        Integer[] second = permuter.next();

        assertThat(first, not(equalTo(second)));
    }
    
    @Test
    public void firstAndSecondNextArePermutationsTest(){
        Integer[] first = permuter.next();
        Integer[] second = permuter.next();
        Arrays.sort(first);
        Arrays.sort(second);

        assertThat(first, equalTo(second));
    }

    @Test(timeout=1)
    public void enoughPermutationsTest(){
        for(int i = 0; i < 23; i++){
            permuter.next();
        }
    }

    @Test(expected=LastPermutationException.class)
    public void throwExceptionAtTheEndTest(){
        for(int i = 0; i < 24; i++){
            permuter.next();
        }
    }

    @Test
    public void allPermutationsSizeTest(){
        List<Integer[]> allPermutations = permuter.getAll();
        assertEquals(allPermutations.size(), 24);
    }

    @Test
    public void allPermutationsAreAllPermutationsTest(){
        List<Integer[]> allPermutations = permuter.getAll();
        areAllPermutationsTest(allPermutations);
    }

    @Test
    public void allNextSizeTest(){
        permuter.next();
        List<Integer[]> allNext = permuter.getAllNext();

        assertEquals(allNext.size(), 23);
    }

    @Test
    public void allNextAreAllPermutationsTest(){
        List<Integer[]> allNext = permuter.getAllNext();
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

