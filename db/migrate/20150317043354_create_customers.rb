class CreateCustomers < ActiveRecord::Migration
  def change
    create_table :customers, id: false do |t|
      t.string :name
      t.integer :phone_number
      
      t.timestamps
    end
  end
end
