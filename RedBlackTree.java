import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T>{
	
	protected static class RBTNode<T> extends Node<T> {
	    public int blackHeight = 0;
	    public RBTNode(T data) { super(data); }
	    public RBTNode<T> getUp() { return (RBTNode<T>)this.up; }
	    public RBTNode<T> getDownLeft() { return (RBTNode<T>)this.down[0]; }
	    public RBTNode<T> getDownRight() { return (RBTNode<T>)this.down[1]; }
	}
	/**
	 * This method checks for RBT violation after insertion and fixes it
	 * @param redNode
	 */
	protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> redNode) {
		//Base case: this node is a root, we are good
		if(this.root.equals(redNode) || this.root.equals(redNode.getUp())) {
			return;
		}
		//Node that we deal with will only be those have grandparent because if 3 nodes are added, they will not cause violation as first 3 nodes will be black to red and red.
		RBTNode<T> parentNode = redNode.getUp();
		RBTNode<T> grandParentNode = parentNode.getUp();
		//Base case: Parent of this node is black, we are good.
		if(parentNode.blackHeight == 1) {
			return;
		}
		/*We check if it is: 
		 * Case 1: The sibling of parent is red too
		 * Case 2: The sibling of parent is black and this node is on the same side as parent
		 * Case 3: The sibling of parent is black and this node is on the the other side as parent
		 * */
		//Case 1
		if(grandParentNode.getDownLeft() != null && grandParentNode.getDownLeft().blackHeight == 0 && grandParentNode.getDownRight() != null && grandParentNode.getDownRight().blackHeight == 0) {
			grandParentNode.blackHeight = 0;
			grandParentNode.getDownLeft().blackHeight = 1;
			grandParentNode.getDownRight().blackHeight = 1;
			//Knowing that now the grandparent may cause the problem, we call this method on the grandparentNode
			enforceRBTreePropertiesAfterInsert(grandParentNode);
		}else {
			//Case 2
			//Same Side
			if((redNode.data.compareTo(parentNode.data) > 0  && parentNode.data.compareTo(grandParentNode.data) > 0) ||
				(redNode.data.compareTo(parentNode.data) < 0  && parentNode.data.compareTo(grandParentNode.data) < 0)) {
				//Perform rotation
				this.rotate(parentNode, grandParentNode);
				grandParentNode.blackHeight = 0;
				parentNode.blackHeight = 1;
				//Checks which side of the grandparentNode has violation and call enforce on it
				if(grandParentNode.getDownLeft() != null && grandParentNode.getDownLeft().blackHeight==0) {
					enforceRBTreePropertiesAfterInsert(grandParentNode.getDownLeft());
				}else if(grandParentNode.getDownRight() != null && grandParentNode.getDownRight().blackHeight==0){
					enforceRBTreePropertiesAfterInsert(grandParentNode.getDownRight());
				}
			//Case 3
			}else {
				//We rotate it and then perform the recursive
				this.rotate(redNode, parentNode);
				//We now pass in the rotated parentNode as it will still have violation
				enforceRBTreePropertiesAfterInsert(parentNode);
			}
		}
	}
	/**
     * Inserts a new data value into the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided data argument is null
     */
	@Override
	public boolean insert(T data) throws NullPointerException {
        if (data == null)
			throw new NullPointerException("Cannot insert data value null into the tree.");
        boolean statusToReturn;
        RBTNode<T> newNode = new RBTNode<T>(data);
        newNode.blackHeight = 0;
        statusToReturn = this.insertHelper(newNode);
		this.enforceRBTreePropertiesAfterInsert(newNode);
		RBTNode<T> rootNode = (RBTNode<T>)this.root;
		rootNode.blackHeight = 1;
		return statusToReturn;
    }
    
	/**
	 * This method test the violation when a red node is added to a red node with the sibling being red too
	 */
	@Test
	public void checkCase1Violation() {
    	RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		tree.insert(7);
		tree.insert(6);
		tree.insert(8);
		tree.insert(9);
    	
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).data == 7 && ((RBTNode<Integer>)tree.root).blackHeight == 1);
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).getDownLeft().blackHeight == 1 && ((RBTNode<Integer>)tree.root).getDownRight().blackHeight == 1);
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).getDownRight().getDownRight().blackHeight == 0);
	}
	
	/**
	 * This method test the violation when a red node is added to a red node on the same side with the sibling being black
	 */
	@Test
	public void checkCase2Violation() {
    	RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		tree.insert(32);
		tree.insert(41);
		tree.insert(57);
		tree.insert(62);
		tree.insert(79);
		tree.insert(81);
		tree.insert(93);
		tree.insert(97);
    	
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).data == 62 && ((RBTNode<Integer>)tree.root).blackHeight == 1);
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).getDownLeft().blackHeight == 0 && ((RBTNode<Integer>)tree.root).getDownRight().blackHeight == 0);
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).getDownRight().getDownRight().getDownRight().blackHeight == 0);
	}
	
	/**
	 * This method test the violation when a red node is added to a red node on the other side with the sibling being black
	 */
	@Test
	public void checkCase3Violation() {
    	RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
		tree.insert(6);
		tree.insert(4);
		tree.insert(11);
		tree.insert(2);
		tree.insert(5);
		tree.insert(8);
		tree.insert(12);
		tree.insert(7);
		tree.insert(9);
		tree.insert(10);
    	
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).data == 8 && ((RBTNode<Integer>)tree.root).blackHeight == 1);
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).getDownLeft().blackHeight == 0 && ((RBTNode<Integer>)tree.root).getDownRight().blackHeight == 0);
    	Assertions.assertTrue(((RBTNode<Integer>)tree.root).getDownRight().getDownLeft().getDownRight().blackHeight == 0);
	}
	
}
