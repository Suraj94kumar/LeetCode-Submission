class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int prefix[]=new int[n];
        int suffix[]=new int[n];
        Stack<Integer> st= new Stack<>();
        for(int i =0; i<n;i++){
            while(st.isEmpty()==false && heights[st.peek()] >=heights[i])st.pop();
            if(st.isEmpty()== false ) prefix[i]=st.peek()+1;
            else prefix[i] = 0;
            st.push(i);
        }
        st.clear();
        for(int i = n-1; i>=0; i--){
            while(st.isEmpty()== false && heights[st.peek()]>=heights[i]) st.pop();
            if(st.isEmpty() == false) suffix[i]=st.peek()-1;
            else suffix[i] = n-1;
            st.push(i);
        }
        int ans= 0;
        for(int i =0; i<n; i++){
            ans = Math.max(ans,heights[i]*(suffix[i]-prefix[i]+1));
        }
        return ans;
    }
    public int maximalRectangle(char[][]matrix){
        int n = matrix.length;
        int m = matrix[0].length;
        int height[]=new int[m];
        int ans =0;
        for(int i = 0; i<n; i++){
            for(int j = 0; j<m;j++){
                if(matrix[i][j]== '1')height[j]++;
                else height[j]= 0;
            }
            ans = Math.max(ans,largestRectangleArea(height));
        }
         return ans;
     
    }
    
}