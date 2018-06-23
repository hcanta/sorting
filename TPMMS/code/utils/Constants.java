package code.utils;
/***
 *  Class of constants.
 * @author hcanta
 */
public class Constants 
{
	/**
	 * prefix for the sorted sublist of data_1
	 */
	public static final String DATA_ONE_SORTED = "Data_One_Sorted_Sublist_";
	/**
	 * Prefix for the sorted sublist of data_2
	 */
	public static final String DATA_TWO_SORTED = "Data_Two_Sorted_Sublist_";
	/**
	 * Path to the resulting join output
	 */
	public static final String JOIN_OUTPUT = "results/join_output.txt";
	/**
	 * Path to the sorted data_1 file
	 */
	public static final String DATA_ONE_OUTPUT = "results/Data_one_output.txt";
	/**
	 * Path to the sorted data_2 file
	 */
	public static final String DATA_TWO_OUTPUT = "results/Data_two_output.txt";
	/**
	 * The number of block sections
	 */
	public static final byte BLOCK_SECTIONS = 3;
	/**
	 * The block sizetupples of data_1 
	 */
	public static final short BLOCK_SIZE = 4000; 
	/**
	 * The amount of data_1 tupples that fits in one block
	 */
	public static final byte NO_DATA_ONE_TUPPLES_ONE_BLOCK = 40; 
	/**
	 * the amount of data_2 tupples that fits in one block
	 */
	public static final byte NO_DATA_TWO_TUPPLES_ONE_BLOCK = 60;
}
