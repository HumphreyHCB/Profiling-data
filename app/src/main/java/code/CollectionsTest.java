package code;
/**
 * CollectionsTest
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.collections4.list.NodeCachingLinkedList;

import com.gs.collections.api.iterator.MutableIntIterator;
import com.gs.collections.api.list.primitive.MutableIntList;
import com.gs.collections.impl.factory.primitive.IntLists;

import gnu.trove.iterator.TIntIterator;
import gnu.trove.list.linked.TIntLinkedList;
import it.unimi.dsi.fastutil.ints.IntArrayList;

public class CollectionsTest {

    static int testfinal = 0;
    public static void main(String[] args) {
        CollectionsTest ct = new CollectionsTest();
        System.out.println("10m: ");
        System.out.println(testfinal);
    }

    public CollectionsTest() {
        int[] array = new int[]{155,545,476,330,657,986,616,680,251,665,980,905,407,194,952,500,394,922,731,517,377,719,751,442,395,349,116,114,944,582,722,875,904,855,447,473,959,895,114,891,593,614,217,545,471,240,442,707,511,61,611,788,263,635,836,237,639,112,13,821,722,918,486,29,738,643,893,791,15,164,663,842,553,920,444,676,276,407,777,735,418,187,28,470,680,584,188,992,223,999,86,856,142,54,949,515,530,813,85,928,159,174,465,117,985,787,866,312,966,322,181,142,493,217,866,227,802,46,611,95,971,386,253,62,835,830,284,188,791,723,120,885,101,178,253,493,825,322,815,384,52,237,551,933,481,946,491,247,930,536,981,155,644,963,30,531,717,479,526,158,315,61,608,9,461,422,301,560,62,570,74,994,528,937,802,466,532,160,195,6,574,264,835,860,377,706,850,166,411,336,368,119,68,897,623,190,836,915,739,488,353,96,827,767,926,599,465,481,656,699,525,715,972,53,670,867,658,216,633,270,978,737,529,851,842,626,731,228,25,278,290,384,128,334,702,630,593,855,124,262,605,613,372,973,779,106,526,697,119,665,624,80,292,505,518,270,819,878,678,107,358,519,447,844,399,640,384,990,48,236,254,334,47,42,33,1,373,478,56,936,564,731,992,657,811,27,308,21,961,736,438,82,706,854,676,676,476,277,474,514,376,129,982,24,540,830,426,121,218,691,810,899,579,722,423,586,674,919,287,504,652,319,703,9,217,744,887,93,89,273,219,478,603,682,648,101,233,225,773,176,630,436,510,558,670,845,728,707,262,499,645,584,715,269,479,298,898,370,553,474,66,347,894,28,164,412,185,931,94,693,767,659,743,725,940,930,283,594,269,183,418,169,499,175,400,119,431,303,582,220,904,859,22,576,142,56,956,622,246,549,752,276,573,450,246,515,663,579,1,492,195,178,254,951,465,606,884,763,372,890,729,790,869,769,150,565,717,673,776,148,338,905,30,805,281,60,31,147,960,315,988,841,544,381,601,139,834,988,73,701,925,277,910,630,358,105,622,645,708,858,626,465,182,545,34,950,555,256,928,239,764,825,833,406,667,975,841,417,793,56,785,146,870,305,293,844,35,114,870,824,488,395,565,443,951,612,830,399,533,996,915,592,477,264,323,472,163,514,591,89,395,487,17,975,803,860,401,333,605,188,894,656,113,909,229,316,27,921,796,117,46,282,747,250,525,215,759,826,982,621,192,813,438,593,297,527,352,754,256,924,445,858,809,215,524,59,830,925,572,162,328,926,707,388,869,554,189,495,272,777,535,64,277,183,817,214,322,428,889,65,458,725,11,825,743,394,413,930,522,252,153,950,693,71,860,317,697,189,280,747,723,5,788,670,777,330,651,682,480,577,580,15,220,988,478,601,129,121,559,946,835,85,385,419,999,561,758,782,820,403,840,587,242,313,659,445,553,635,852,332,555,648,168,502,317,883,386,760,390,244,98,974,141,184,269,396,354,734,675,882,167,630,793,497,268,458,135,188,649,717,469,102,248,288,82,538,790,356,438,16,96,792,920,79,848,935,487,250,220,650,52,684,601,615,416,118,616,614,193,290,202,378,539,731,936,977,925,449,53,487,976,598,957,813,769,7,574,595,624,422,33,481,204,421,118,499,529,144,754,911,581,493,314,376,634,157,701,861,84,972,205,703,363,583,235,205,618,296,908,533,955,912,314,791,283,227,403,808,729,836,616,308,813,491,731,156,422,167,93,353,874,966,287,373,893,809,420,595,320,630,164,673,928,882,238,884,824,247,965,364,434,611,532,268,527,688,425,48,756,355,384,473,556,171,994,858,737,110,16,796,731,550,684,496,56,2,873,641,918,687,861,133,326,785,403,89,658,830,798,562,964,107,692,103,268,392,850,182,449,766,22,66,93,871,428,836,825,604,242,629,264,591,243,197,964,928,746,886,567,122,37,693,370,67,255,970,909,698,215,715,786,707,847,208,168,48,513,488,678,621,988,634,403,734,933,298,366,72,545,408,646,941,717,727,631,504,282,177,866,860,757,502,470,709,267,587,612,678,972,865,611,113,243,234,24,89,677,274,271,990,587,892,12,221,611,115,802,218,145,682,534,590,539,255,738,345,2,597,136,635,305,382,693,981,441,687,301,17,196,613,402,196,191,966,612,136,735,83,490,704,389,477,391,644,80,789,823,337,983,87,310,790,388,195,677,922,180,991,374,921,200,149,538,407,748,845,405,987,245,891,472,973,462,287,821,632,255,388,727,118};
        int[] arr10x = x10array(x10array(x10array(x10array(array))));
        int total = 0;

        
        HashMap<Integer,Integer> ArrayTestMap = arrayListTest(arr10x);
        HashMap<Integer,Integer> LinkedListTestMap = LinkedListTest(arr10x);
        HashMap<Integer,Integer> VectorTestMap =  VectorTest(arr10x);
        HashMap<Integer,Integer> StackTestMap = StackTest(arr10x);
        HashMap<Integer,Integer> nodeCachingLinkedListMap = NodeCachingLinkedListTest(arr10x);
        HashMap<Integer,Integer> IntArrayListMap = IntArrayListTest(arr10x);
        HashMap<Integer,Integer> TIntLinkedListMap = TIntLinkedListTest(arr10x);
        HashMap<Integer,Integer> GoldmanSachsIntListMap = GoldmanSachsIntListTest(arr10x);
        // the hash map returned should be size 637, so the total should be 5,096  
        total = ArrayTestMap.size() + 
                LinkedListTestMap.size() + 
                VectorTestMap.size() + 
                StackTestMap.size() + 
                nodeCachingLinkedListMap.size() + 
                IntArrayListMap.size() +
                TIntLinkedListMap.size() +
                GoldmanSachsIntListMap.size();
        testfinal = total;
    }

    // map an array to an arrayList
    // and return the occurrence of each number
    public HashMap<Integer,Integer> arrayListTest(int[] arr) {
        ArrayList<Integer> arrlist = new ArrayList<Integer>();
        for (int i : arr) {
            arrlist.add(i);
        }

        return occurrences(arrlist);
        
    }

    // map an array to an Linked List
    // and return the occurrence of each number
    public HashMap<Integer,Integer> LinkedListTest(int[] arr) {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        for (int i : arr) {
            linkedList.add(i);
        }

        return occurrences(linkedList);
    }

    // map an array to an Vector
    // and return the occurrence of each number
    public HashMap<Integer,Integer> VectorTest(int[] arr) {
        Vector<Integer> vector = new Vector<Integer>();
        for (int i : arr) {
            vector.add(i);
        }

        return occurrences(vector);
    }

    // map an array to an Stack
    // and return the occurrence of each number
    public HashMap<Integer,Integer> StackTest(int[] arr) {
        Stack<Integer> stack = new Stack<Integer>();
        for (int i : arr) {
            stack.add(i);
        }

        return occurrences(stack);
    }

    // map an array to an NodeCachingLinkedListTest
    // and return the occurrence of each number
    public HashMap<Integer,Integer> NodeCachingLinkedListTest(int[] arr) {
        NodeCachingLinkedList<Integer> nodeCachingLinkedListTest = new NodeCachingLinkedList<Integer>();
        for (int i : arr) {
            nodeCachingLinkedListTest.add(i);
        }

        return occurrences(nodeCachingLinkedListTest);
    }

    // map an array to an IntArrayList
    // and return the occurrence of each number
    public HashMap<Integer,Integer> IntArrayListTest(int[] arr) {
        IntArrayList IntArrayList = new IntArrayList();
        for (int i : arr) {
            IntArrayList.add(i);
        }

        return occurrences(IntArrayList);
    } 

    // map an array to an TIntLinkedList
    // and return the occurrence of each number
    public HashMap<Integer,Integer> TIntLinkedListTest(int[] arr) {
        TIntLinkedList tIntLinkedList = new TIntLinkedList();
        for (int i : arr) {
            tIntLinkedList.add(i);
        }
        
        return occurrences(tIntLinkedList);
    } 

    // map an array to an GoldmanSachs Int List
    // and return the occurrence of each number
    public HashMap<Integer,Integer> GoldmanSachsIntListTest(int[] arr) {
        MutableIntList GSIntList = IntLists.mutable.of();
        for (int i : arr) {
            GSIntList.add(i);
        }
        
        return occurrences(GSIntList);
    } 

    // takes an gerneric collection of type interger and hash maps every occurrency
    public HashMap<Integer,Integer> occurrences(Collection<Integer> collection) {
        HashMap<Integer,Integer> heatMap = new HashMap<>();
        for (int i : collection) {
            if (heatMap.containsKey(i)) {
                heatMap.put(i, heatMap.get(i) + 1);
            }
            else{
                heatMap.put(i, 1);
            }
        }
        collection.clear(); // test of the collecetions speed of clearing
        return heatMap;
    }

    private HashMap<Integer, Integer> occurrences(TIntLinkedList collection) {
        HashMap<Integer,Integer> heatMap = new HashMap<>();
        TIntIterator iterator = collection.iterator();
        int x = 0;
        while (iterator.hasNext()) {
            x = iterator.next();
            if (heatMap.containsKey(x)) {
                heatMap.put(x, heatMap.get(x) + 1);
            }
            else{
                heatMap.put(x, 1);
            }
        }
    
        collection.clear(); // test of the collecetions speed of clearing
        return heatMap;
    }

    // overloead for MutableIntList
    public HashMap<Integer,Integer> occurrences(MutableIntList collection) {
        HashMap<Integer,Integer> heatMap = new HashMap<>();
        MutableIntIterator iterator = collection.intIterator();
        int x = 0;
        while (iterator.hasNext()) {
            x = iterator.next();
            if (heatMap.containsKey(x)) {
                heatMap.put(x, heatMap.get(x) + 1);
            }
            else{
                heatMap.put(x, 1);
            }
        }
    
        collection.clear(); // test of the collecetions speed of clearing
        return heatMap;
    }
    

    // repeat an array 10 times
    public int[] x10array(int[] arr) {
        int[] x4array = repeatArray(repeatArray(arr));
        int[] x5array = new int[x4array.length + arr.length];
        System.arraycopy(x4array, 0, x5array, 0, x4array.length);
        System.arraycopy(arr, 0, x5array, x4array.length, arr.length);
        return repeatArray(x5array);
    }

    static int[] repeatArray(int[] array) {

        int[] result = new int[array.length * 2];
        System.arraycopy(array, 0, result, 0, array.length);
        System.arraycopy(array, 0, result, array.length, array.length);
        return result;
    }

}
