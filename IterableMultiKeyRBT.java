// --== CS400 Fall 2023 File Header Information ==--
// Name: Tan Siong Ann
// Email: stan83@wisc.edu
// Group: B12
// TA: -
// Lecturer: Gary
// Notes to Grader: N/A
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/**
 * This class extends RedBlackTree and models the tree but with KeyList as nodes that store multiple values (keys)
 * @author Sean
 *
 * @param <T> our generic, could be anything that extends Comparable such as integer
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>> implements IterableMultiKeySortedCollectionInterface<T>{
	private Comparable<T> startingPoint = null;
	private int numberOfKey = 0;
	/**
     * Inserts value into tree that can store multiple objects per key by keeping
     * lists of objects in each node of the tree.
     * @param key object to insert
     * @return true if a new node was inserted, false if the key was added into an existing node
     */
	@Override
	public boolean insertSingleKey(T key) {
		//Create a keyList
		KeyList<T> newKeyList = new KeyList<T>(key);
		Node<KeyListInterface<T>> node = this.findNode(newKeyList);
		//Increment number of key because we are inserting it anyway
		this.numberOfKey++;
		//If returned node from findNode is null then key doesn't exist yet as node
		//We insert it to tree
		if(node==null) {
			this.insert(newKeyList);
			return true;
		}
		//node is not null, we add key to it
		node.data.addKey(key);
		//return false
		return false;
	}
	
	/**
     * @return the number of values in the tree.
     */
	@Override
	public int numKeys() {
		return this.numberOfKey;
	}

	/**
     * Returns an iterator that does an in-order iteration over the tree.
     */
	@Override
	public Iterator<T> iterator() {
		/**
		 * Our iterator class
		 * @author Sean
		 *
		 */
		class MultiKeyRBTIterator implements Iterator<T>{
			private Stack<Node<KeyListInterface<T>>> stack = new Stack<Node<KeyListInterface<T>>>();
			private Queue<T> queue = new LinkedList<T>();
			
			//Constructor that create a stack for iterating
			public MultiKeyRBTIterator(){
				this.stack = getStartStack();
				//Now we check and get the rest of the node in order
				//If stack is not empty
				if(!(this.stack.empty())){
					//Get first node
					Node<KeyListInterface<T>> startingNode = this.stack.pop();
					//clear the stack
					this.stack.clear();
					//check if there's more on the right side
					Node<KeyListInterface<T>> temp = startingNode;
					while(true) {
						//checks if left child exists
						//if not in stack and bigger than starting then we go left
						if(temp != null && temp.down[1]!=null && !this.stack.contains(temp.down[1]) && temp.down[1].data.compareTo(startingNode.data)>0) {
							//Go right
							temp = temp.down[1];
						}else {
							//If self >= starting node & not exist
							//push self
							if(temp != null && temp.data.compareTo(startingNode.data)>=0 && !this.stack.contains(temp)) {
								this.stack.push(temp);
								for(T t: temp.data) {
									this.queue.add(t);
								}
							}
							//Go right if 
							//not exidt in stack and bigger than starting
							if(temp != null && temp.down[0]!=null && !this.stack.contains(temp.down[0]) && temp.down[0].data.compareTo(startingNode.data)>0) {
								temp = temp.down[0];
							}else{
								if(temp.up != null){
									temp = temp.up;
								}else{
									break;
								}
							}
						}
					}
				}
			}
			
			@Override
			public boolean hasNext() {
				//return true if stack not empty
				//return false if stack empty
				return !(this.queue.isEmpty());
			}

			@Override
			public T next() {
				//return next in stack
				return this.queue.remove();
			}
		}
		//return the iterator
		return new MultiKeyRBTIterator();
	}
	
	/**
	 * 
	 * @return	Stack object with all the nodes bigger or equal to the starting point
	 * @throws illegalArgumentException when the tree node contains null key
	 */
	protected Stack<Node<KeyListInterface<T>>> getStartStack() {
		Stack<Node<KeyListInterface<T>>> path = new Stack<Node<KeyListInterface<T>>>();
		Node<KeyListInterface<T>> startingNode = this.root;
		Node<KeyListInterface<T>> temp = startingNode;
		//if starting point is null we go the bottom left
		if(this.startingPoint == null) {
			while(temp!=null) {
				path.push(temp);
				temp = temp.down[0];
			}
		//if starting point given, we go to it and only include what is bigger
		}else {
			boolean continueLooking = true;
			while(temp != null && temp.data != null && continueLooking) {
				T compareObject = temp.data.iterator().next();
				if(compareObject == null) {
					throw new IllegalArgumentException("Compared data has null as key.");
				}
				//starting point is bigger. Go right and not push any node in
				if(this.startingPoint.compareTo(compareObject)>0) {
					temp = temp.down[1];
				//starting point is smaller. Go left and push the node in
				}else if(this.startingPoint.compareTo(temp.data.iterator().next())<0) {
					path.push(temp);
					temp = temp.down[0];
				//starting point is equal. Push the node in and end loop
				}else {
					path.push(temp);
					continueLooking = false;
				}
			}
		}
		return path;
	}
	
	/**
     * Sets the starting point for iterations. Future iterations will start at the
     * starting point or the key closest to it in the tree. This setting is remembered
     * until it is reset. Passing in null disables the starting point.
     * @param startPoint the start point to set for iterations
     */
	@Override
	public void setIterationStartPoint(Comparable<T> startPoint) {
		if(startPoint == null) {
			this.startingPoint = null;
			return;
		}
		this.startingPoint = startPoint;
	}
	
	/**
	 * This method clears the tree and sets the number of key to 0
	 */
	@Override
	public void clear() {
        this.clear();
        this.numberOfKey = 0;
    }
	
    /**
     * This test should checks the insertSingleKey method and make sure it's correct
     */
	@Test
	public void checkInsertSingleKey() {
		//We will now implement a simple test and we will modify it as week goes by
		IterableMultiKeyRBT<Integer> rbt = new IterableMultiKeyRBT<Integer>();
		rbt.insertSingleKey(5);
		rbt.insertSingleKey(7);
		Assertions.assertEquals(2, rbt.numKeys());
		rbt.insertSingleKey(5);
		Assertions.assertEquals(3, rbt.numKeys());
		Assertions.assertEquals(2, rbt.size());
	}
	
	/**
     * This test should checks the iterator method and make sure it's correct
     */
	@Test
	public void checkIterator() {
		//We will now implement a simple test and we will modify it as week goes by
		IterableMultiKeyRBT<Integer> rbt = new IterableMultiKeyRBT<Integer>();
		rbt.insertSingleKey(5);
		rbt.insertSingleKey(7);
		rbt.insertSingleKey(15);
		rbt.insertSingleKey(20);
		rbt.insertSingleKey(22);
		rbt.insertSingleKey(5);
		rbt.insertSingleKey(7);
		rbt.insertSingleKey(15);
		rbt.insertSingleKey(20);
		rbt.insertSingleKey(22);
		int expected[] = {5, 5, 22, 22, 20, 20, 15, 15, 7, 7};
		int i = 0;
		for(int value:rbt) {
			Assertions.assertEquals(expected[i], value);
			i++;
		}
	}
	
	/**
     * This test should checks the setStartingPoint method and make sure it's correct
     */
	@Test
	public void checkSetStartingPoint() {
		//We will now implement a simple test and we will modify it as week goes by
		IterableMultiKeyRBT<Integer> rbt = new IterableMultiKeyRBT<Integer>();
		rbt.insertSingleKey(5);
		rbt.insertSingleKey(7);
		rbt.insertSingleKey(15);
		rbt.insertSingleKey(20);
		rbt.insertSingleKey(22);
		rbt.insertSingleKey(21);
		rbt.insertSingleKey(8);
		rbt.insertSingleKey(5);
		rbt.insertSingleKey(7);
		rbt.insertSingleKey(15);
		rbt.insertSingleKey(20);
		rbt.insertSingleKey(22);
		rbt.insertSingleKey(21);
		rbt.insertSingleKey(8);
		int expected[] = {15, 15, 22, 22, 21, 21, 20, 20};
		rbt.setIterationStartPoint(15);
		int i = 0;
		for(int value:rbt) {
			Assertions.assertEquals(expected[i], value);
			i++;
		}
	}
}
