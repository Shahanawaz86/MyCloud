#define lli long long int
using namespace std;
#include<bits/stdc++.h>
#include<iostream>
long long int modi = 1000000007;

lli mul(lli x, lli y) {return (x * 1LL * y)%modi; }

lli power(lli x, lli y)
{
  lli ans = 1;
  while(y>0){
    if(y&1) ans = mul(ans, x);

    x = mul(x,x);
    y = y>>1;

  }

  return ans;
}

lli devide(lli x, lli y){   
  return mul(x, power(y, (modi-2)));
}

int main()
{

string a, b;
cin>>a>>b;

int n = a.size();
int m = b.size();



int ctr = 0;
int ans = 0;

for(int i=0; i<m; i++)
{
  if(a[i] != b[i])
  ctr++;
}

if(ctr%2 == 0)
ans++;

int i = m, j = 0;
while(i<n)
{
  
}



}   

