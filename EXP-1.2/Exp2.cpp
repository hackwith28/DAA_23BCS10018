#include <iostream>
using namespace std;

double power(double x, int y) {
    if (y == 0) {
        return 1;
    }

    double temp = power(x, y / 2);

    if (y % 2 == 0) {
        return temp * temp;
    } else {
        if (y > 0) {
            return x * temp * temp;
        } else {
            return (temp * temp) / x;
        }
    }
}

int main() {
    cout << "2^10 = " << power(2, 10) << endl;
    cout << "2^-3 = " << power(2, -3) << endl;
    cout << "5^0 = " << power(5, 0) << endl;
    cout << "3^7 = " << power(3, 7) << endl;

    return 0;
}
