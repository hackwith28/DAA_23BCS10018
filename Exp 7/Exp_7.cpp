/*
    Aim: Develop a program and analyze complexity to implement 0-1 Knapsack using Dynamic Programming.
    A thief is robbing a store and can carry a maximal weight of W into his knapsack. There are n items and weight of ith item is wi and the profit of selecting this item is pi. What items should the thief take?
 
    Let i be the highest-numbered item in an optimal solution S for W dollars. Then S' = S - {i} is an optimal solution for W - wi dollars and the value to the solution S is Vi plus the value of the sub-problem.
    We can express this fact in the following formula: define c[i, w] to be the solution for items 1,2, … , i and the maximum weight w.
    The algorithm takes the following inputs
    ·      The maximum weight W
    ·      The number of items n
    ·      The two sequences v = <v1, v2, …, vn> and w = <w1, w2, …, wn>
*/

#include <bits/stdc++.h>
using namespace std;

int unboundedKnapsack(int n, int W, vector<int>& val, vector<int>& wt) {
    vector<vector<int>> dp(n, vector<int>(W + 1, 0));

    for (int i = wt[0]; i <= W; i++) {
        dp[0][i] = (i / wt[0]) * val[0];
    }

    for (int ind = 1; ind < n; ind++) {
        for (int cap = 0; cap <= W; cap++) {
            int notTaken = 0 + dp[ind - 1][cap];

            int taken = INT_MIN;
            if (wt[ind] <= cap)
                taken = val[ind] + dp[ind][cap - wt[ind]];

            dp[ind][cap] = max(notTaken, taken);
        }
    }

    return dp[n - 1][W];
}

int main() {
    vector<int> wt = { 2, 4, 6 };
    vector<int> val = { 5, 11, 13 };
    int W = 10;
    int n = wt.size();

    cout << "The maximum value of items the thief can steal is " << unboundedKnapsack(n, W, val, wt) << endl;

    return 0;
}
