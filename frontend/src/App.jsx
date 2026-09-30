import './App.css'
import { useState, useEffect } from 'react'

function App() {
  const [formData, setFormData] = useState({
    firstname: '',
    lastname: '',
    email: '',
    department: '',
    salary: ''
  })

  const [employees, setEmployees] = useState([])

  // This stores the ID of the employee being updated
  const [editId, setEditId] = useState(null)

  const handleChange = (e) => {
    const { id, value } = e.target

    setFormData(prevState => ({
      ...prevState,
      [id]: value
    }))
  }

  
  // GET - READ
  // =========================

  const getEmployees = () => {
    fetch('http://localhost:8080/api/employees')
      .then(response => response.json())
      .then(data => {
        setEmployees(data)
      })
      .catch(error => {
        console.error('Error fetching employees:', error)
      })
  }

  useEffect(() => {
    getEmployees()
  }, [])


  // =========================
  // CREATE + UPDATE
  // =========================

  const handleSubmit = (e) => {
    e.preventDefault()

    const employeeData = {
      firstName: formData.firstname,
      lastName: formData.lastname,
      email: formData.email,
      department: {
        id: Number(formData.department)
      },
      salary: Number(formData.salary)
    }


    // =========================
    // UPDATE
    // =========================

    if (editId !== null) {

      fetch(`http://localhost:8080/api/employees/${editId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(employeeData)
      })
        .then(response => {
          if (response.ok) {
            console.log('Employee updated successfully')

            setEditId(null)

            setFormData({
              firstname: '',
              lastname: '',
              email: '',
              department: '',
              salary: ''
            })

            getEmployees()
          } else {
            console.error('Error updating employee')
          }
        })
        .catch(error => {
          console.error('Error updating employee:', error)
        })

    }

    // =========================
    // CREATE
    // =========================

    else {

      fetch('http://localhost:8080/api/employees', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(employeeData)
      })
        .then(response => {
          if (response.ok) {
            console.log('Employee data submitted successfully')

            setFormData({
              firstname: '',
              lastname: '',
              email: '',
              department: '',
              salary: ''
            })

            getEmployees()
          } else {
            console.error('Error submitting employee data')
          }
        })
        .catch(error => {
          console.error('Error submitting employee data:', error)
        })
    }
  }


  // =========================
  // EDIT
  // =========================

  const handleEdit = (employee) => {

    setEditId(employee.id)

    setFormData({
      firstname: employee.firstName,
      lastname: employee.lastName,
      email: employee.email,
      department: employee.department?.id || '',
      salary: employee.salary
    })
  }


  // =========================
  // DELETE
  // =========================

  const handleDelete = (id) => {

    const confirmDelete = window.confirm(
      'Are you sure you want to delete this employee?'
    )

    if (!confirmDelete) {
      return
    }

    fetch(`http://localhost:8080/api/employees/${id}`, {
      method: 'DELETE'
    })
      .then(response => {

        if (response.ok) {
          console.log('Employee deleted successfully')

          getEmployees()
        } else {
          console.error('Error deleting employee')
        }

      })
      .catch(error => {
        console.error('Error deleting employee:', error)
      })
  }


  return (
    <div className="container">
      <h1> Employee Form</h1>

      <form onSubmit={handleSubmit}>

        <label htmlFor="firstname" >Name:</label>
        <input
          type="text"
          id="firstname"
          value={formData.firstname}
          onChange={handleChange}
          placeholder="Enter your  First Name"
        />

        <label htmlFor="lastname" >Last Name:</label>
        <input
          type="text"
          id="lastname"
          value={formData.lastname}
          onChange={handleChange}
          placeholder="Enter your Last Name"
        />

        <label htmlFor="email" >Email:</label>
        <input
          type="email"
          id="email"
          value={formData.email}
          onChange={handleChange}
          placeholder="Enter your Email"
        />

        <label htmlFor="department">Department:</label>

        <select
          id="department"
          value={formData.department}
          onChange={handleChange}
        >
          <option value="">Select Department</option>
          <option value="1">IT</option>
          <option value="2">HR</option>
          <option value="3">Finance</option>
          <option value="4">Sales</option>
        </select>

        <label htmlFor="salary" >Salary:</label>

        <input
          type="number"
          id="salary"
          value={formData.salary}
          onChange={handleChange}
          placeholder="Enter your Salary"
        />

        <input
          type="submit"
          value={editId !== null ? "Update" : "Submit"}
        />

      </form>


 {/* Employee list - added below existing form  */}

      <h2>Employees</h2>

      <table>

        <thead>
          <tr>
            <th>ID</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Department</th>
            <th>Salary</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>

          {employees.map(employee => (

            <tr key={employee.id}>

              <td>{employee.id}</td>

              <td>{employee.firstName}</td>

              <td>{employee.lastName}</td>

              <td>{employee.email}</td>

              <td>{employee.department?.name}</td>

              <td>{employee.salary}</td>

              <td>

                <button
                  type="button"
                  onClick={() => handleEdit(employee)}
                >
                  Edit
                </button>

                <button
                  type="button"
                  onClick={() => handleDelete(employee.id)}
                >
                  Delete
                </button>

              </td>

            </tr>

          ))}

        </tbody>

      </table>

    </div>
  )
}

export default App