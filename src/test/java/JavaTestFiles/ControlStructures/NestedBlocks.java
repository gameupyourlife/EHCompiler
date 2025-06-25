class NestedBlocks {
    int nestedBlock(int x) {
    {
      int y = x * 2;
      {               
        x = y + 1;
      }                
    }                 
    return x;
  } 
}
