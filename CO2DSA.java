class SegmentTree {

    int[] tree;
    int n;

    SegmentTree(int[] arr) {

        n = arr.length;
        tree = new int[4 * n];

        build(1,0,n-1,arr);
    }

    private void build(int node,int start,int end,int[] arr) {

        if(start == end) {
            tree[node] = arr[start];
            return;
        }

        int mid = (start + end)/2;

        build(2*node,start,mid,arr);
        build(2*node+1,mid+1,end,arr);

        tree[node] =
                Math.max(tree[2*node],tree[2*node+1]);
    }

    public int queryMax(int node,int start,int end,
                        int left,int right) {

        if(right < start || left > end)
            return Integer.MIN_VALUE;

        if(left <= start && end <= right)
            return tree[node];

        int mid = (start + end)/2;

        int p1 = queryMax(2*node,start,mid,left,right);
        int p2 = queryMax(2*node+1,mid+1,end,left,right);

        return Math.max(p1,p2);
    }

    public void update(int node,int start,int end,
                       int idx,int value) {

        if(start == end) {
            tree[node] = value;
            return;
        }

        int mid = (start + end)/2;

        if(idx <= mid)
            update(2*node,start,mid,idx,value);
        else
            update(2*node+1,mid+1,end,idx,value);

        tree[node] =
                Math.max(tree[2*node],tree[2*node+1]);
    }

    public static void main(String[] args) {

        int[] readings =
                {71,73,78,75,82,79,77,80};

        SegmentTree st =
                new SegmentTree(readings);

        System.out.println(
                "Max [3..7] = "
                        + st.queryMax(1,0,7,2,6));

        st.update(1,0,7,3,88);

        System.out.println(
                "Max [2..6] after update = "
                        + st.queryMax(1,0,7,1,5));
    }
}