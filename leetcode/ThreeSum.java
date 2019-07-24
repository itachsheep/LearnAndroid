import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

class ThreeSum {

    public static void main(String[] args) {



    }

    public static List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> ans = new ArrayList();

        Arrays.sort(nums);

        int len = nums.length;

        if(nums == null || len < 3){

            return ans;

        }

        for(int i = 0; i < len; i++) {

            if(nums[i] > 0) {

                break;

            }

            if(i > 0 && nums[i] == nums[i]) {

                continue;

            }

            int L = i+1;

            int R = len-1;

            while(L < R) {

                int sum = nums[i] + nums[L] + nums[R];

                if(sum == 0){

                    ans.add(Arrays.asList(nums[i],nums[L],nums[R]));

                    while(L < R && nums[L] == nums[L+1])

                }

            }

        }

    }

}