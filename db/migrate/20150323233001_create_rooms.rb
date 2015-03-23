class CreateRooms < ActiveRecord::Migration
  def change
    create_table :rooms, id: false, :primary_key => :r_number do |t|
      t.integer :r_number
      t.string :r_type
      t.timestamps
    end
  end
end
