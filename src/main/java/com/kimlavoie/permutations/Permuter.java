package org.kimlavoie.permutations;

import java.util.LinkedList;
import java.util.List;

public class Permuter<T>{
    private T[] original;
    private int permutationIndex;
    private int mainObjectIndex;
    private int currentSwapObjectIndex;
    private List<T[]> permutations = new LinkedList<T[]>();
    private List<T[]> nextPermutationBatch = new LinkedList<T[]>();
    private boolean end = false;


    public Permuter(T[] original){
        this.original = original;
        permutations.add(this.original);
        permutationIndex = 0;
        mainObjectIndex = this.original.length - 2;
        currentSwapObjectIndex = mainObjectIndex + 1;
    }

    public T[] next(){
        T[] newPermutation = permute();
        nextPermutationBatch.add(newPermutation);
        updateIndexes();
        
        return newPermutation.clone();
    }

    private T[] permute(){
        if(end) throw new LastPermutationException();
        T[] currentPermutation = permutations.get(permutationIndex).clone();
        for(int i = mainObjectIndex; i < currentSwapObjectIndex; ++i){
            T temp = currentPermutation[i];
            currentPermutation[i] = currentPermutation[i+1];
            currentPermutation[i+1] = temp;
        }
        return currentPermutation;
    }

    private void updateIndexes(){
        if(currentSwapObjectIndex == original.length-1){
            if(permutationIndex == permutations.size()-1){
                permutationIndex = 0;
                mainObjectIndex--;
                if(mainObjectIndex < 0) end = true;
                permutations.addAll(nextPermutationBatch);
                nextPermutationBatch = new LinkedList<T[]>();
            }
            else{
                permutationIndex++;
            }
            currentSwapObjectIndex = mainObjectIndex + 1;
        }
        else{
            currentSwapObjectIndex++;
        }
    }

    public List<T[]> getAll(){
        try{
            while(true) next();
        }catch(LastPermutationException ex){}
        return new LinkedList(permutations);
    }

    public List<T[]> getAllNext(){
        int current = permutations.size() + nextPermutationBatch.size() - 1;
        getAll();
        return new LinkedList(permutations.subList(current, permutations.size()));
    }

}
