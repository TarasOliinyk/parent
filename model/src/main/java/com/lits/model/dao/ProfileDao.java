package com.lits.model.dao;

import com.lits.model.entity.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfileDao {
    private Connection connection;

    public ProfileDao(Connection connection) {
        this.connection = connection;
    }

    public List<Profile> findAll() {
        Statement s = null;
        List<Profile> result = new ArrayList<>();

        try {
            s = connection.createStatement();
            ResultSet rs = s.executeQuery("SELECT id, name, last_name, age FROM profile");

            while (rs.next()) {
                Profile profile = new Profile();
                profile.setId(rs.getInt("id"));
                profile.setName(rs.getString("name"));
                profile.setLastName(rs.getString("last_name"));
                profile.setAge(rs.getInt("age"));
                result.add(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (s != null) {

                try {
                    s.close();
                } catch (SQLException e1) {
                    System.out.println("problem with database");
                }
            }
        }
        return result;
    }

    public Profile findById(int id) {
        PreparedStatement statement = null;
        Profile profile = null;

        try {
            statement = connection.prepareStatement("SELECT id, name, last_name, age FROM profile WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                profile = new Profile();
                profile.setId(resultSet.getInt("id"));
                profile.setName(resultSet.getString("name"));
                profile.setLastName(resultSet.getString("last_name"));
                profile.setAge(resultSet.getInt("age"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (statement != null) {

                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Problem with database");
                }
            }
        }

        if (profile != null) {
            return profile;
        } else {
            throw new RuntimeException(String.format("Profile with id '%s' has not been found", id));
        }
    }

    public Profile save(Profile profile) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement("INSERT INTO profile (name, last_name, age) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getLastName());
            ps.setInt(3, profile.getAge());

            int r = ps.executeUpdate();

            if (r > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    profile.setId(rs.getInt(1));
                }
            } else {
                throw new SQLException("Can not save new profile"); // ToDo: Ask lecture about this exception - in case it is triggered, it will be caught by the parent 'catch' block and the method will return profile object without id
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            if (ps != null) {

                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("problem with database");
                }
            }
        }
        return profile;
    }

    public Profile update(Profile profile) {
        PreparedStatement statement = null;
        boolean result = false;

        try {
            statement = connection.prepareStatement("UPDATE profile SET name = ?, last_name = ?, age = ? WHERE id = ?");
            statement.setString(1, profile.getName());
            statement.setString(2, profile.getLastName());
            statement.setInt(3, profile.getAge());
            statement.setInt(4, profile.getId());
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (statement != null) {

                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Problem with database");
                }
            }
        }

        if (result) {
            return profile;
        } else {
            throw new RuntimeException("Profile has not been updated");
        }
    }

    public boolean deleteProfile(int profileId) {
        PreparedStatement statement = null;
        boolean result = false;

        try {
            statement = connection.prepareStatement("DELETE FROM profile WHERE id = ?");
            statement.setInt(1, profileId);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (statement != null) {

                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println("Problem with database");
                }
            }
        }
        return result;
    }
}
