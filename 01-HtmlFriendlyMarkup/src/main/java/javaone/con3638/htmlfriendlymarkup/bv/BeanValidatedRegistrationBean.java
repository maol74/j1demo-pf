/*
 * Copyright (C) 2013 Martin Fousek & Mert Caliskan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package javaone.con3638.htmlfriendlymarkup.bv;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Named
@SessionScoped
public class BeanValidatedRegistrationBean implements Serializable {

    @Size(min = 1, message = "Name is required")
    private String name;

    @Size(min = 1, message = "Telephone is required")
    @Pattern(regexp = "[0-9]+", message = "Telephone must be a number")
    private String tel;

    @Size(min = 1, message = "Email is required")
    @Email
    private String email;

    /**
     * Creates a new instance of RegistrationBean
     */
    public BeanValidatedRegistrationBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProgress() {
        int progress = 0;
        if (getName() != null && !getName().isEmpty()) progress++;
        if (getTel() != null && !getTel().isEmpty()) progress++;
        if (getEmail() != null && !getEmail().isEmpty()) progress++;
        return String.valueOf(progress);
    }

    public void savePerson() {
        FacesContext.getCurrentInstance().addMessage("status", new FacesMessage(getName() + " was registered!"));
        // save to DB
        reset();
    }

    public void reset() {
        setName(null);
        setTel(null);
        setEmail(null);
    }
    
}