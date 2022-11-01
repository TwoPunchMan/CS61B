package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class BSTNode {
        private K key;
        private V value;
        private BSTNode left;
        private BSTNode right;
        private int size;

        private BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.size = 1;
        }
    }

    private BSTNode root;

    public BSTMap() {
        this.root = null;
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public boolean containsKey(K key) {
        BSTNode b = search(this.root, key);
        return null != b;
    }

    private BSTNode search(BSTNode root, K key) {
        if (root == null) {
            return null;
        }

        if (key.equals(root.key)) {
            return root;
        } else if (key.compareTo(root.key) < 0) {
            return search(root.left, key);
        } else {
            return search(root.right, key);
        }
    }

    @Override
    public V get(K key) {
        if (containsKey(key)) {
            BSTNode node = search(this.root, key);
            return node.value;
        }

        return null;
    }

    @Override
    public int size() {
        return getTreeSize(this.root);
    }

    private int getTreeSize(BSTNode node) {
        if (node == null) {
            return 0;
        }

        return getTreeSize(node.left) + 1 + getTreeSize(node.right);
    }

    @Override
    public void put(K key, V value) {
        this.root = insertKey(this.root, key, value);
    }

    private BSTNode insertKey(BSTNode subroot, K key, V value) {
        if (subroot == null) {
            subroot = new BSTNode(key, value);
            return subroot;
        }

        if (key.compareTo(subroot.key) < 0) {
            subroot.left = insertKey(subroot.left, key, value);
        } else if (key.compareTo(this.root.key) > 0) {
            subroot.right = insertKey(subroot.right, key, value);
        }

        return subroot;
    }

    public void printInOrder() {
        printAscending(this.root);
    }

    private void printAscending(BSTNode root) {
        if (root == null) {
            return;
        }

        printAscending(root.left);
        System.out.println(root.key);
        printAscending(root.right);
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
