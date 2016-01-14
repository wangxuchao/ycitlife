package cn.wangxuchao.ycitz.dao;

import java.util.List;

import cn.wangxuchao.ycitz.model.Pizza;

public interface PizzaDAO {

	public void add(Pizza pizza);

	public void edit(Pizza pizza);

	public void delete(int id);

	public Pizza getPizza(int id);

	public List<Pizza> findAll();
}
