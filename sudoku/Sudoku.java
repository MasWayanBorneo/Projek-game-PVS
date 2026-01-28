package sudoku;

import java.util.Random;

public class Sudoku {
    
    
    private int size;
    private int[][] tab;
    private int[][] tabToSolve;

    public Sudoku(){
    	this.size = 9;
	this.tab = new int[size][size];
        this.tabToSolve = new int[size][size];
    }

    public void createSudoku(int k){
        tab = new int[size][size];
    	fillSudoku(0,0);
        removeDigit(k);
    }
    
    public int[][] getTabToSolve(){
        return tabToSolve;
    }
    
    public int[][] getSolution(){
        return tab;
    }
    
    public void updateTabToSolve(int num, int i, int j){
        this.tabToSolve[i][j] = num;
    }

    private boolean fillSudoku(int i, int j){
	if(i<size && j==size){
            i++;
            j = 0;
	}
	if(i==size && j<size){
            return true;
	}
	int[] a = this.generateRand();
	for(int n=0;n<size;n++){
            if(checkSector(i,j,a[n]) && checkRow(i,a[n]) && checkColumn(j,a[n])){
		tab[i][j] = a[n];
		if(fillSudoku(i,j+1)){
                    return true;
		}
            }
            tab[i][j] = 0;

	}
	return false;

    }

    private boolean checkRow(int row, int num){
	boolean result = true;
	int i = 0;
	while(result && i < size){
            if(tab[row][i] == num){
		result = false;
            }
            i++;
	}
	return result;
    }

    private boolean checkColumn(int col, int num){
	boolean result = true;
	int i = 0;
	while(result && i < size){
            if(tab[i][col] == num){
                result = false;
            }
            i++;
	}
	return result;
    }

    private boolean checkSector(int row, int col, int num){
        boolean result = true;
	int initSectRow = (row/3)*3;
	int initSectCol = (col/3)*3;
	int i = initSectRow;
	int j = initSectCol;
	while(i < initSectRow+3 && result){
            j = initSectCol;
            while(j < initSectCol+3 && result){
		if(tab[i][j] == num){
                    result = false;
		}
		j++;
                if(j == (initSectCol+3)){
                    i++;
                }
            }
	}
	return result;
    }

    private int[] generateRand(){
	int[] a = new int[size];
	Random r = new Random();
	int i = 0;
	while(i<size){
            int n = r.nextInt(size)+1;
            int j = 0;
            boolean contains = false;
            while(j < size && !contains && a[j] != 0){
		if(n == a[j]){
                    contains = true;
		}
		j++;
            }
            if(!contains){
		a[i] = n;
		i++;
            }
	}
	return a;
    }

    private void removeDigit(int k){
    	if(checkExist()){
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    tabToSolve[i][j] = tab[i][j];
                }
            }
            Random r = new Random();
            while(k>0){
		int i = r.nextInt(size);
		int j = r.nextInt(size);
		if(tabToSolve[i][j] != 0){
                    tabToSolve[i][j] = 0;
                    k--;
		}
            }
	}
    }

    public boolean checkExist(){
	boolean result = true;
	for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
		if(tab[i][j] == 0){
                    result = false;
		}
            }
	}
	return result;
    }
    
    public boolean checkSolution(){
        boolean result = true;
        int i = 0;
        int j = 0;
        while(result && i<size){
            j = 0;
            while(result && j<size){
                if(tab[i][j] != tabToSolve[i][j]){
                    result = false;
                }
                j++;
            }
            i++;
        }
        return result;
    }

    public void printSudoku(){
	for(int i = 0;i<size;i++){
            for(int j = 0;j<size;j++){
                System.out.print(tab[i][j]+" ");
            }
            System.out.println();
	}
        System.out.println();
    }

    public void printSudokuToSolve(){
	for(int i = 0;i<size;i++){
            for(int j = 0;j<size;j++){
		System.out.print(tabToSolve[i][j]+" ");
            }
            System.out.println();
	}
        System.out.println();
    }    
    
}
