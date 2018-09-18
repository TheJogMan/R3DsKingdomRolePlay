package jogLib.commandAPI;

import java.util.ArrayList;
import java.util.List;

public abstract class Category extends CategoryParent
{
	String name;
	CategoryParent parent;
	String description;
	List<String> aliases;
	
	void initialize(String name, String description, CategoryParent parent)
	{
		if (parent.getCategory(name) != null)
		{
			throw new IllegalStateException("Category name [" + name + "] already taken in provided CategoryParent!");
		}
		
		this.name = name;
		this.description = description;
		this.parent = parent;
		aliases = new ArrayList<String>();
		
		parent.categories.add(this);
	}
	
	public Category(CategoryParent parent, String name, String description)
	{
		initialize(name, description, parent);
	}
	
	public boolean checkName(String name)
	{
		return checkName(name, true);
	}
	
	public boolean checkName(String name, boolean checkAliases)
	{
		name = name.toLowerCase();
		if (name.compareTo(this.name.toLowerCase()) == 0)
		{
			return true;
		}
		else
		{
			if (checkAliases)
			{
				return aliases.contains(name);
			}
			else
			{
				return false;
			}
		}
	}
	
	public void addAlias(String alias)
	{
		aliases.add(alias.toLowerCase());
	}
	
	public List<String> getAliases()
	{
		return aliases;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getFullName()
	{
		return parent.getFullName() + " " + getName();
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public CategoryParent getParent()
	{
		return parent;
	}
}