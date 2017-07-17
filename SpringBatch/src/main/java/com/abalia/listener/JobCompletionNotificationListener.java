package com.abalia.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.abalia.model.Persona;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate=jdbcTemplate;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.listener.JobExecutionListenerSupport#afterJob(org.springframework.batch.core.JobExecution)
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		if(jobExecution.getStatus() == BatchStatus.COMPLETED){
			log.debug("!!! JOB TERMINADO! Es hora de verificar el resultado ");
			
			List<Persona> listado = jdbcTemplate.query("SELECT nombre, apellidos FROM persona", new RowMapper<Persona>() {

				@Override
				public Persona mapRow(ResultSet rs, int row) throws SQLException {
					// TODO Auto-generated method stub
					return new Persona(rs.getString(1), rs.getString(2));
				}
			});
			
			for(Persona p : listado){
				log.debug("Encontrado <" + p + "> en la BB.DD.");
			}
		}
	}
}
