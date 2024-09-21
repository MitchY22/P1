import java.util.LinkedList;
import java.util.Stack;


/**
 * Binary Search Tree implementation with a Node inner class for representing
 * the nodes of the tree. We will turn this Binary Search Tree into a self-balancing
 * tree as part of project 1 by modifying its insert functionality.
 * In week 0 of project 1, we will start this process by implementing tree rotations.
 */
public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {

    /**
     * This class represents a node holding a single value within a binary tree.
     */
    protected static class Node<T> {
        public T data;

        // up stores a reference to the node's parent
        public Node<T> up;
        // The down array stores references to the node's children:
        // - down[0] is the left child reference of the node,
        // - down[1] is the right child reference of the node.
        // The @SupressWarning("unchecked") annotation is use to supress an unchecked
        // cast warning. Java only allows us to instantiate arrays without generic
        // type parameters, so we use this cast here to avoid future casts of the
        // node type's data field.
        @SuppressWarnings("unchecked")
        public Node<T>[] down = (Node<T>[])new Node[2];
        public Node(T data) { this.data = data; }
        
        /**
         * @return true when this node has a parent and is the right child of
         * that parent, otherwise return false
         */
        public boolean isRightChild() {
            return this.up != null && this.up.down[1] == this;
        }

    }

    protected Node<T> root; // reference to root node of tree, null when empty
    protected int size = 0; // the number of values in the tree

    /**
     * Inserts a new data value into the tree.
     * This tree will not hold null references, nor duplicate data values.
     * @param data to be added into this binary search tree
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided data argument is null
     */
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
			throw new NullPointerException("Cannot insert data value null into the tree.");
		return this.insertHelper(new Node<>(data));
    }

    /**
     * Performs a naive insertion into a binary search tree: adding the new node
     * in a leaf position within the tree. After this insertion, no attempt is made
     * to restructure or balance the tree.
     * @param node the new node to be inserted
     * @return true if the value was inserted, false if is was in the tree already
     * @throws NullPointerException when the provided node is null
     */
    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
        if(newNode == null) throw new NullPointerException("new node cannot be null");

        if (this.root == null) {
            // add first node to an empty tree
            root = newNode;
            size++;
            return true;
        } else {
            // insert into subtree
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                	return false;
				} else if (compare < 0) {
                    // insert in left subtree
                    if (current.down[0] == null) {
                        // empty space to insert into
                        current.down[0] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[0];
                    }
                } else {
                    // insert in right subtree
                    if (current.down[1] == null) {
                        // empty space to insert into
                        current.down[1] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        // no empty space, keep moving down the tree
                        current = current.down[1]; 
                    }
                }
            }
        }
    }

    /**
     * Performs the rotation operation on the provided nodes within this tree.
     * When the provided child is a left child of the provided parent, this
     * method will perform a right rotation. When the provided child is a
     * right child of the provided parent, this method will perform a left rotation.
     * When the provided nodes are not related in one of these ways, this method
     * will throw an IllegalArgumentException.
     * @param child is the node being rotated from child to parent position
     *      (between these two node arguments)
     * @param parent is the node being rotated from parent to child position
     *      (between these two node arguments)
     * @throws IllegalArgumentException when the provided child and parent
     *      node references are not initially (pre-rotation) related that way
     */
    protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        //check for illegal arugument
    	if(child == null || parent == null) {
    		throw new IllegalArgumentException("Child and Parent node has to NOT be null.");
    	}
    	//Check if child is related or not
    	boolean isRightChild = child.isRightChild();
    	boolean isRelated = (parent.down[0] != null && parent.down[0].equals(child) 
    				|| parent.down[1] != null && parent.down[1].equals(child)) ? true : false;
    	//Now we check if the child is right or left child and related
    	//Throw exception if it is illegal
    	if(isRightChild && isRelated) {
    		//Point A left child to B right child
    		parent.down[1] = null;
    		Node<T> childLeft = child.down[0];
    		parent.down[1] = childLeft;
    		//Make sure childLeft is not null
    		if(childLeft != null) {
    			childLeft.up = parent;
    		}
    		child.down[0] = null;
    		//Point B to A on right side
    		child.down[0] = parent;
    	}else if(!isRightChild && isRelated){
    		//Point A left child to B right child
    		parent.down[0] = null;
    		Node<T> childRight = child.down[1];
    		parent.down[0] = childRight;
    		//Make sure childRight is not null
    		if(childRight != null) {
    			childRight.up = parent;
    		}
    		child.down[1] = null;
    		//Point B to A on right side
    		child.down[1] = parent;
    	}else {
    		throw new IllegalArgumentException("Provided child node is not a child on left or right of the parent node.");
    	}
    	//Get the node before parent
    	Node<T> grandparent = parent.up;
    	//check if grandparent exists and change it to child
    	if(grandparent != null) {
   			//check if this parent is right or left child
    		if (grandparent.down[0] != null && grandparent.down[0].equals(parent)) {
    			grandparent.down[0] = child;
 			}else {
    			grandparent.down[1] = child;
    		}
    		child.up = grandparent;
    	}else {
    		this.root = child;
    		child.up = null;
    	}
    	//change parent up to child 
    	parent.up = child;
    	
    }

	/**
     * Get the size of the tree (its number of nodes).
     * @return the number of nodes in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Method to check if the tree is empty (does not contain any node).
     * @return true of this.size() returns 0, false if this.size() != 0
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Checks whether the tree contains the value *data*.
     * @param data a comparable for the data value to check for
     * @return true if *data* is in the tree, false if it is not in the tree
     */
    public boolean contains(Comparable<T> data) {
        // null references will not be stored within this tree
        if (data == null) {
            throw new NullPointerException("This tree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNode(data);
            // return false if the node is null, true otherwise
            return (nodeWithData != null);
        }
    }

    /**
     * Removes all keys from the tree.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Helper method that will return the node in the tree that contains a specific
     * key. Returns null if there is no node that contains the key.
     * @param data the data value for which we want to find the node that contains it
     * @return the node that contains the data value or null if there is no such node
     */
    protected Node<T> findNode(Comparable<T> data) {
        Node<T> current = this.root;
        while (current != null) {
            int compare = data.compareTo(current.data);
            if (compare == 0) {
                // we found our value
                return current;
            } else if (compare < 0) {
                if (current.down[0] == null) {
                    // we have hit a null node and did not find our node
                    return null;
                }
                // keep looking in the left subtree
                current = current.down[0];
            } else {
                if (current.down[1] == null) {
                    // we have hit a null node and did not find our node
                    return null;
                }
                // keep looking in the right subtree
                current = current.down[1];
            }
        }
        return null;
    }

    /**
     * This method performs an inorder traversal of the tree. The string 
     * representations of each data value within this tree are assembled into a
     * comma separated string within brackets (similar to many implementations 
     * of java.util.Collection, like java.util.ArrayList, LinkedList, etc).
     * @return string containing the ordered values of this tree (in-order traversal)
     */
    public String toInOrderString() {
        // generate a string of all values of the tree in (ordered) in-order
        // traversal sequence
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if(!nodeStack.isEmpty() || popped.down[1] != null) sb.append(", ");
                    current = popped.down[1];
                } else {
                    nodeStack.add(current);
                    current = current.down[0];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    /**
     * This method performs a level order traversal of the tree. The string
     * representations of each data value
     * within this tree are assembled into a comma separated string within
     * brackets (similar to many implementations of java.util.Collection).
     * This method will be helpful as a helper for the debugging and testing
     * of your rotation implementation.
     * @return string containing the values of this tree in level order
     */
    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.down[0] != null) q.add(next.down[0]);
                if(next.down[1] != null) q.add(next.down[1]);
                sb.append(next.data.toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }

    // Implement at least 3 tests using the methods below. You can
    // use your notes from lecture for ideas of rotation examples to test with.
    // Make sure to include rotations at the root of a tree in your test cases.
    // Give each of the methods a meaningful header comment that describes what is being
    // tested and make sure your tests have inline comments that help with reading your test code.
    // If you'd like to add additional tests, then name those methods similar to the ones given below.
    // Eg: public static boolean test4() {}
    // Do not change the method name or return type of the existing tests.
    // You can run your tests through the static main method of this class.
    /**
     * This method will test the simplest rotation case with no more parent for the parent node
     * @return true if test case is passed; false otherwise.
     */
    public static boolean test1() {
        //Create a BST
    	BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
    	// find nodes for 4, 2, and 1
    	bst.insert(7);
    	bst.insert(4);
    	bst.insert(3);
        Node<Integer> seven = bst.root;
        Node<Integer> four = seven.down[0];
        Node<Integer> three = four.down[0];
        //perform right rotate
        //check the order of the tree to make sure it is in right order
        bst.rotate(four, seven);
        if(!bst.toLevelOrderString().equals("[ 4, 3, 7 ]")) {
        	return false;
        }
        //Check if it is still a valid tree
        if(!bst.toInOrderString().equals("[ 3, 4, 7 ]")) {
        	return false;
        }
        //Now we try left rotate
        //We insert 2 and 1
        bst.insert(8);
        bst.insert(5);
        //System.out.println(bst);
        //Now level order is [ 4, 3, 7, 5, 8 ]
        bst.rotate(seven, four);
        //Make sure it is in right order (But a wrong order at the bottom of the tree can give same string)
        if(!bst.toLevelOrderString().equals("[ 7, 4, 8, 3, 5 ]")) {
        	return false;
        }
        //So we use this to make sure the bottom nodes are correct
        if(!seven.down[0].down[0].equals(three)) {
        	return false;
        }
        //Check if it is still a valid tree
        if(!bst.toInOrderString().equals("[ 3, 4, 5, 7, 8 ]")) {
        	return false;
        }
        
        return true;
    }
    
    /**
     * This method test rotation on a BST middle node that has a parent
     * @return true if the test case passed; false otherwise.
     */
    public static boolean test2() {
    	//Create a BST
    	BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
    	// find nodes for 4, 2, and 1
    	bst.insert(7);
    	bst.insert(4);
    	bst.insert(10);
    	bst.insert(2);
    	bst.insert(5);
    	bst.insert(9);
    	bst.insert(11);
    	bst.insert(1);
    	bst.insert(3);
    	bst.insert(6);
        Node<Integer> seven = bst.root;
        Node<Integer> four = seven.down[0];
        Node<Integer> two = four.down[0];
        Node<Integer> five = four.down[1];
        //perform right rotate
        bst.rotate(two, four);
        //Make sure the tree is in correct order
        if(!bst.toLevelOrderString().equals("[ 7, 2, 10, 1, 4, 9, 11, 3, 5, 6 ]")) {
        	return false;
        }
        //Check if it is still a valid tree
        if(!bst.toInOrderString().equals("[ 1, 2, 3, 4, 5, 6, 7, 9, 10, 11 ]")) {
        	return false;
        }
        //Now we try left rotate
        bst.rotate(five, four);
        if(!bst.toLevelOrderString().equals("[ 7, 2, 10, 1, 5, 9, 11, 4, 6, 3 ]")) {
        	return false;
        }
        //make sure the left rotated bst in the right order
        if(!seven.down[0].down[1].equals(five)) {
        	return false;
        }
        //Check if it is still a valid tree
        if(!bst.toInOrderString().equals("[ 1, 2, 3, 4, 5, 6, 7, 9, 10, 11 ]")) {
        	return false;
        }
        return true;
    }
    /**
     * This test invalid situation and make sure it throws exception
     * @return true if test case passed; false otherwise.
     */
    public static boolean test3() {
    	//Create a BST
    	BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
    	// find nodes for 4, 2, and 1
    	bst.insert(7);
    	bst.insert(4);
    	bst.insert(10);
    	bst.insert(2);
    	bst.insert(5);
    	bst.insert(9);
    	bst.insert(11);
    	bst.insert(1);
    	bst.insert(3);
    	bst.insert(6);
        Node<Integer> seven = bst.root;
        Node<Integer> four = seven.down[0];
        Node<Integer> two = four.down[0];
        //perform rotate on a non-child
        try {
        	bst.rotate(two, seven);
        	//If it gets to here it is a failed case because we want it to throw IllegalArgumentException
        	return false;
        }catch(IllegalArgumentException e){
        	//pass
        }catch(Exception e) {
        	return false;
        }
        //perform rotation on parent to child
        try {
        	bst.rotate(seven, four);
        	//If it gets to here it is a failed case because we want it to throw IllegalArgumentException
        	return false;
        }catch(IllegalArgumentException e){
        	//pass
        }catch(Exception e) {
        	return false;
        }
        
        return true;
    }
    //Do another test that use the most bottom child of the tree
    /**
     * This test invalid situation and make sure it throws exception
     * @return true if test case passed; false otherwise.
     */
    public static boolean test4() {
    	//Create a BST
    	BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
    	// find nodes for 4, 2, and 1
    	bst.insert(7);
    	bst.insert(4);
    	bst.insert(10);
    	bst.insert(2);
    	bst.insert(5);
    	bst.insert(9);
    	bst.insert(11);
        Node<Integer> seven = bst.root;
        Node<Integer> four = seven.down[0];
        Node<Integer> ten = seven.down[1];
        Node<Integer> two = four.down[0];
        Node<Integer> five = four.down[1];
        Node<Integer> nine = ten.down[0];
        Node<Integer> eleven = ten.down[1];
        //perform left rotate on bottom node
        bst.rotate(eleven, ten);
        //Make sure the tree is in correct order
        if(!bst.toLevelOrderString().equals("[ 7, 4, 11, 2, 5, 10, 9 ]")) {
        	return false;
        }
        //Check if it is still a valid tree
        if(!bst.toInOrderString().equals("[ 2, 4, 5, 7, 9, 10, 11 ]")) {
        	return false;
        }
        //A final check to make sure everything is in right position
        if(!ten.up.equals(eleven) || !ten.down[0].equals(nine) || ten.down[1] != null) {
        	return false;
        }
        
        //perform right rotate on bottom node
        bst.rotate(two, four);
        //Make sure the tree is in correct order
        if(!bst.toLevelOrderString().equals("[ 7, 2, 11, 4, 10, 5, 9 ]")) {
        	return false;
        }
        //Check if it is still a valid tree
        if(!bst.toInOrderString().equals("[ 2, 4, 5, 7, 9, 10, 11 ]")) {
        	return false;
        }
        //A final check to make sure everything is in right position
        if(!four.up.equals(two) || !four.down[1].equals(five) || four.down[0] != null) {
        	return false;
        }
        
        return true;
    }
    
    /**
     * Main method to run tests. If you'd like to add additional test methods, add a line for each
     * of them.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Test 1 passed: " + test1());
        System.out.println("Test 2 passed: " + test2());
        System.out.println("Test 3 passed: " + test3());
        System.out.println("Test 4 passed: " + test4());
    }

}
