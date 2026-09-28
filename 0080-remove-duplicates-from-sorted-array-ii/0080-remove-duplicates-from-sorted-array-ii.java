class Solution {
    public int removeDuplicates(int[] nums) {
        int j=0;
        for(int num:nums){
            if(j<2||num!=nums[j-2]){
                nums[j]= num;
                j++;
            }
        }
        return j;
    }
}