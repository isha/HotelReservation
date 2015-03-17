class Customer < ActiveRecord::Base
  self.primary_keys = :name, :phone_number
end
