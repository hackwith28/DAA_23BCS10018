/*
    Aim: Develop a program and analyze complexity to find all occurrences of a pattern P in a given stringS.
*/

#include <bits/stdc++.h>
using namespace std;

#define d 256 

void rabinKarp(string text, string pattern, int q) {
    int n = text.length();
    int m = pattern.length();
    int i, j;
    int p = 0;
    int t = 0;
    int h = 1;

    for (i = 0; i < m - 1; i++)
        h = (h * d) % q;

    for (i = 0; i < m; i++) {
        p = (d * p + pattern[i]) % q;
        t = (d * t + text[i]) % q;
    }

    for (i = 0; i <= n - m; i++) {
        if (p == t) {
            bool match = true;
            for (j = 0; j < m; j++) {
                if (text[i + j] != pattern[j]) {
                    match = false;
                    break;
                }
            }
            if (match)
                cout << "Pattern found at index " << i << endl;
        }

        if (i < n - m) {
            t = (d * (t - text[i] * h) + text[i + m]) % q;

            if (t < 0)
                t = (t + q);
        }
    }
}

int main() {
    string text = "ABCCDDAEFG";
    string pattern = "CDD";
    int q = 101;

    rabinKarp(text, pattern, q);
    return 0;
}
