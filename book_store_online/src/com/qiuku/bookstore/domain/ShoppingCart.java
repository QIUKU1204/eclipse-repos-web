package com.qiuku.bookstore.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @TODO: ShoppingCart(购物车)实体类 
 * @author:QIUKU
 */
public class ShoppingCart {
	// 实体类属性 bookShoppingCart
	// 键值对映射<Integer, ShoppingCartItem>的集合
	private Map<Integer, ShoppingCartItem> bookShoppingCart = new HashMap<>();
	/**
	 * 返回整个映射集合
	 */
	public Map<Integer, ShoppingCartItem> getBooks() {
		return bookShoppingCart;
	}
	
	/**
	 * 修改指定购物项的数量
	 */
	public void updateItemQuantity(Integer id, int quantity){
		ShoppingCartItem sci = bookShoppingCart.get(id);
		if(sci != null){
			sci.setQuantity(quantity);
		}
	}
	
	/**
	 * 移除指定的购物项
	 * @param id
	 */
	public void removeItem(Integer id){
		bookShoppingCart.remove(id);
	}
	
	/**
	 * 清空购物车
	 */
	public void clear(){
		bookShoppingCart.clear();
	}
	
	/**
	 * 返回购物车是否为空
	 * @return
	 */
	public boolean isEmpty(){
		return bookShoppingCart.isEmpty();
	}
	
	/**
	 * 获取购物车中所有的商品的总的钱数
	 * @return
	 */
	public float getTotalMoney(){
		float total = 0;		
		for(ShoppingCartItem sci: getItems()){
			total += sci.getItemMoney();
		}		
		return total;
	}
	
	/**
	 * 获取购物车中的所有的 ShoppingCartItem 组成的集合
	 * @return
	 */
	public Collection<ShoppingCartItem> getItems(){
		return bookShoppingCart.values();
	}
	
	/**
	 * 返回购物车中商品的总数量
	 * @return
	 */
	public int getBookNumber(){
		int total = 0;	
		for(ShoppingCartItem sci: bookShoppingCart.values()){
			total += sci.getQuantity();
		}	
		return total;
	}
	
	/**
	 * 检验购物车中是否有 id 指定的商品		
	 * @param id
	 * @return
	 */
	public boolean hasBook(Integer id){
		return bookShoppingCart.containsKey(id);
	}		
			
	/**
	 * 向购物车中添加一件商品		
	 * @param book
	 */
	public void addBook(Book book){
		// 检查购物车中有没有该商品, 若有, 则使其数量 +1; 
		// 若没有, 则新建其对应的 ShoppingCartItem, 并把其加入到 books 中
		ShoppingCartItem sci = bookShoppingCart.get(book.getId());
		
		if(sci == null){ // 没有
			sci = new ShoppingCartItem(book);
			bookShoppingCart.put(book.getId(), sci);
		}else{ // +1
			sci.increment();
		}
	}
}
