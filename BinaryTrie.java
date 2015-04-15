
/*
 * BinaryTrie has functions:
 * 1. void insert(IP key, IP value)
 * 2. IP get(IP key)
 * 3. boolean existKey(IP key)
 * 4. void optimize() : do a post order traversal to optimize this trie
 */
class Node {
	Node left = null;
	Node right = null;
	boolean isLeaf = false;

	public boolean isLeaf() {
		return isLeaf;
	}
}

class LeafNode extends Node {
	public IP key = null;
	public IP value = null;
	LeafNode(IP ip, IP ip2) {
		super();
		isLeaf = true;
		this.key = ip;
		this.value = ip2;
	}
}

public class BinaryTrie {
	Node root = new Node();
	public BinaryTrie(){
//		System.out.println("con");
	}

	public boolean existKey(String str){
		IP ip = new IP(str);
		int[] info = new int[2];
		info[1] = 0;
		Node parent = locateParent(ip, info);
		if(info[0] == 0){
			if(parent.left != null && ((LeafNode)parent.left).key.equals(ip))
				return true;
		}else{
			if(parent.right != null && ((LeafNode)parent.right).key.equals(ip))
				return true;
		}
		return false;
	}
	//after post traversal, we can retrive the value based on most length prefix
	public IP get(IP ip){
		Node cur = root;
		for(int i = 0; i < IP.LENGTH; ++i){
			if(ip.getByte(i) == 0){
				cur = cur.left;
				if(cur.isLeaf())
					return ((LeafNode)(cur)).value;
			}else{
				cur = cur.right;
				if(cur.isLeaf())
					return ((LeafNode)(cur)).value;
			}
		}
		return null;
	}
	public String getPrefix(IP ip){
		StringBuilder sb = new StringBuilder();
		Node cur = root;
		for(int i = 0; i < IP.LENGTH; ++i){
			if(ip.getByte(i) == 0){
				cur = cur.left;
				sb.append('0');
				if(cur.isLeaf())
					return sb.toString();
			}else{
				cur = cur.right;
				sb.append('1');
				if(cur.isLeaf())
					return sb.toString();
			}
		}
		return null;
	}
		
//	public IP get(IP key){
//		int[] info = new int[2];
//		info[1] = 0;
//		Node parent = locateParent(key, info);
//		if(info[0] == 0){
//			if(parent.left != null && ((LeafNode)parent.left).key.equals(key))
//				return ((LeafNode)parent.left).value;
//		}else{
//			if(parent.right != null && ((LeafNode)parent.right).key.equals(key))
//				return ((LeafNode)parent.right).value;
//		}
//		return null;
//	}
	public void insert(IP key, IP value) {
		int[] info = new int[2];
		info[1] = 0;
		Node parent = locateParent(key, info);
		
		if (info[0] == 0) {
			if (parent.left == null) {
				LeafNode newLeaf = new LeafNode(key, value);
				parent.left = newLeaf;
				return;
			}
			LeafNode l = (LeafNode) parent.left;
			if (l.key.equals(key))
				return;
			parent.left = extendTrie(info[1] + 1, l, new LeafNode(key, value));
		} else {
			if (parent.right == null) {
				LeafNode newLeaf = new LeafNode(key, value);
				parent.right = newLeaf;
				return;
			}
			LeafNode l = (LeafNode) parent.right;
			if (l.key.equals(key))
				return;
			else {
				parent.right = extendTrie(info[1] + 1, l, new LeafNode(key, value));
			}
		}

	}

	/*
	 * int[0] indicates the child is whether the leftchild(0) or rightchild(1) of its parent
	 * int[1] indicates which level in the trie the parent node is in.	
	 */
	private Node locateParent(IP ip, int[] info) {
		Node cur = root;
		for (int i = 0; i < IP.LENGTH; ++i) {
			if (ip.getByte(i) == 0) {
				if (cur.left == null || cur.left.isLeaf()) {
					info[0] = 0;
					return cur;
				}
				info[1]++;
				cur = cur.left;
			} else {
				if (cur.right == null || cur.right.isLeaf()) {
					info[0] = 1;
					return cur;
				}
				info[1]++;
				cur = cur.right;
			}
		}
		return cur;
	}

	private Node extendTrie(int level, LeafNode orgLeaf, LeafNode newLeaf) {
		Node tmp = new Node();
		Node head = tmp;
		for (int i = level; i < IP.LENGTH; ++i) {
			int orgBit = orgLeaf.key.getByte(i);
			int newBit = newLeaf.key.getByte(i);
			if (orgBit == newBit) {
				Node newNode = new Node();
				if (orgBit == 0)
					tmp.left = newNode;
				else
					tmp.right = newNode;
				tmp = newNode;
			} else {
				if (orgBit == 0) {
					tmp.left = orgLeaf;
					tmp.right = newLeaf;
				} else {
					tmp.left = newLeaf;
					tmp.right = orgLeaf;
				}
				return head;
			}
		}
		return head;
	}
	
	//do a post order traversal to optimize the trie
	private static LeafNode FeatureNode = new LeafNode(new IP("0.0.0.0"), new IP("0.0.0.0"));
	public void optimize(){
		traversal(root);
	}
	private LeafNode traversal(Node root){
		if(root == null)
			return null;
		if(root.isLeaf())
			return (LeafNode)root;
		LeafNode left = traversal(root.left);
		LeafNode right = traversal(root.right);
		
		if(left != FeatureNode)
			root.left = left;
		if(right != FeatureNode)
			root.right = right;
		
		if(left == FeatureNode || right == FeatureNode){
			return FeatureNode;
		}
		if(left == null || right == null){
			if(left == null)
				return right;
			else
				return left;
		}else{
			if(left.value.equals(right.value)){
				return left;
			}else{
				return FeatureNode;
			}
		}
	}
}





