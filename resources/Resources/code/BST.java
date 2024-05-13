public class BST<K extends Comparable<K>, T> implements Map<K, T> {
    private Node root;
    private int size;

    private class Node {
        K key;
        T data;
        Node left, right;

        Node(K key, T data) {
            this.key = key;
            this.data = data;
        }
    }

    public BST() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public T retrieve() {
        return root == null ? null : root.data;
    }

    @Override
    public void update(T e) {
        if (root != null) root.data = e;
    }

    @Override
    public boolean find(K key) {
        Node node = findNode(root, key);
        return node != null;
    }

    private Node findNode(Node current, K key) {
        if (current == null) return null;
        int cmp = key.compareTo(current.key);
        if (cmp < 0) return findNode(current.left, key);
        else if (cmp > 0) return findNode(current.right, key);
        else return current;
    }

    @Override
    public int nbKeyComp(K key) {
        return findWithCount(root, key, 0);
    }

    private int findWithCount(Node current, K key, int count) {
        if (current == null) return count;
        count++;
        int cmp = key.compareTo(current.key);
        if (cmp < 0) return findWithCount(current.left, key, count);
        else if (cmp > 0) return findWithCount(current.right, key, count);
        else return count;
    }

    @Override
    public boolean insert(K key, T data) {
        Node addedNode = insertRec(root, key, data);
        if (addedNode != null) {
            size++;
            return true;
        }
        return false;
    }

    private Node insertRec(Node current, K key, T data) {
        if (current == null) {
            current = new Node(key, data);
            return current;
        }
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = insertRec(current.left, key, data);
        } else if (cmp > 0) {
            current.right = insertRec(current.right, key, data);
        } else {
            return null; // Key already exists
        }
        return current;
    }

    @Override
    public boolean remove(K key) {
        if (find(key)) {
            root = removeRec(root, key);
            size--;
            return true;
        }
        return false;
    }

    private Node removeRec(Node current, K key) {
        if (current == null) return null;
        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = removeRec(current.left, key);
        } else if (cmp > 0) {
            current.right = removeRec(current.right, key);
        } else {
            // Node with only one child or no child
            if (current.left == null)
                return current.right;
            else if (current.right == null)
                return current.left;

            // Node with two children: Get the inorder successor (smallest in the right subtree)
            current.data = minValue(current.right);
            current.key = minKey(current.right);
            current.right = removeRec(current.right, current.key);
        }
        return current;
    }

    private K minKey(Node root) {
        K minKey = root.key;
        while (root.left != null) {
            minKey = root.left.key;
            root = root.left;
        }
        return minKey;
    }

    private T minValue(Node root) {
        T minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

    @Override
    public DLLComp<K> getKeys() {
        DLLComp<K> keys = new DLLCompImp<>();
        inOrderKeys(root, keys);
        return keys;
    }

    private void inOrderKeys(Node node, DLLComp<K> keys) {
        if (node != null) {
            inOrderKeys(node.left, keys);
            keys.insert(node.key);
            inOrderKeys(node.right, keys);
        }
    }
}
